package staysplit.hotel_reservation.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.domain.common.auth.JwtTokenProvider;
import staysplit.hotel_reservation.domain.common.auth.dto.AccessTokenDto;
import staysplit.hotel_reservation.domain.common.auth.dto.GoogleProfileDto;
import staysplit.hotel_reservation.domain.common.auth.dto.KakaoProfileDto;
import staysplit.hotel_reservation.domain.common.auth.dto.RedirectDto;
import staysplit.hotel_reservation.domain.common.auth.service.GoogleService;
import staysplit.hotel_reservation.domain.common.auth.service.KakaoService;
import staysplit.hotel_reservation.domain.customer.domain.dto.*;
import staysplit.hotel_reservation.domain.common.exception.AppException;
import staysplit.hotel_reservation.domain.common.exception.ErrorCode;
import staysplit.hotel_reservation.domain.customer.domain.Customer;
import staysplit.hotel_reservation.domain.customer.domain.Role;
import staysplit.hotel_reservation.domain.customer.repository.CustomerRepository;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final GoogleService googleService;
    private final KakaoService kakaoService;


    // 일반 회원가입
    public Customer signup(CustomerCreateRequest customerCreateRequest) {

        if (customerRepository.existsByEmail(customerCreateRequest.email())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
        }

        Customer customer = Customer.builder()
                .email(customerCreateRequest.email())
                .password(encoder.encode(customerCreateRequest.password()))
                .role(Role.CUSTOMER)
                .name(customerCreateRequest.name())
                .birthdate(customerCreateRequest.birthdate())
                .nickname(customerCreateRequest.nickname())
                .build();

        customerRepository.save(customer);
        return customer;
    }

    // oauth로 회원가입
    public CustomerInfoResponse oauthSignup(String socialId, String email, String name, LocalDate birthdate,
                            String nickname) {

        if (customerRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
        }

        if (customerRepository.existsBySocialId(socialId)) {
            throw new AppException(ErrorCode.DUPLICATE_SOCIAL_ID, ErrorCode.DUPLICATE_SOCIAL_ID.getMessage());
        }

        Customer customer = Customer.builder()
                .email(email)
                .role(Role.CUSTOMER)
                .name(name)
                .birthdate(birthdate)
                .nickname(nickname)
                .socialId(socialId) // no password
                .build();

        customerRepository.save(customer);
        return CustomerInfoResponse.from(customer);
    }

    // 일반 로그인
    public String login(CustomerLoginRequest loginRequest) {
        Customer customer = validateCustomer(loginRequest.email());
        if (!encoder.matches(loginRequest.password(), customer.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }
        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
    }

    // 구글 로그인
    public String googleLogin(RedirectDto redirectDto) {
        AccessTokenDto accessTokenDto = googleService.getAccessToken(redirectDto.getCode());
        GoogleProfileDto googleProfileDto = googleService.getGoolgleProfile(accessTokenDto.getAccessToken());

        // 회원가입이 되어 있지 않다면 회원가입
        Customer customer = customerRepository.findBySocialId(googleProfileDto.getSub());
        if (customer == null) {
            // 추가 정보 입력 받기
           throw new AppException(ErrorCode.ADDITIONAL_INFO_REQUIRED, ErrorCode.ADDITIONAL_INFO_REQUIRED.getMessage());
        }

        // 회원가입 된 회원은 jwt 토큰 발급
        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
    }

    // 카카오 로그인
    public String kakaoLogin(RedirectDto redirectDto) {
        AccessTokenDto accessTokenDto = kakaoService.getAccessToken(redirectDto.getCode());
        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(accessTokenDto.getAccessToken());

        // 회원가입이 되어 있지 않다면 추가 정보 입력 받고, 회원가입 (oauthSignup())
        Customer customer = customerRepository.findBySocialId(kakaoProfileDto.getId());
        if (customer == null) {
            throw new AppException(ErrorCode.ADDITIONAL_INFO_REQUIRED, ErrorCode.ADDITIONAL_INFO_REQUIRED.getMessage());
        }

        // 회원가입 된 회원은 jwt 토큰 발급
        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
    }


    public CustomerInfoResponse findCustomerById(Long id) {
        Customer customer = validateCustomer(id);
        return CustomerInfoResponse.from(customer);
    }

    public CustomerInfoResponse changeNickname(NicknameChangeRequest request, String email) {
        Customer customer = validateCustomer(email);
        if (customerRepository.existsByNickname(request.getNickname())) {
            throw new AppException(ErrorCode.DUPLICATE_NICKNAME, ErrorCode.DUPLICATE_NICKNAME.getMessage());
        }
        customer.setNickname(request.getNickname());
        return CustomerInfoResponse.from(customer);
    }

    // 두번째 이메일은 Authentication 객체에 있는 값
    public CustomerInfoResponse changeEmail(EmailChangeRequest emailChangeRequest, String email) {
        Customer customer = validateCustomer(email);
        if (customerRepository.existsByEmail(emailChangeRequest.getEmail())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
        }
        customer.changeEmail(emailChangeRequest.getEmail());
        return CustomerInfoResponse.from(customer);
    }

    public void delete(Long customerId) {
        Customer customer = validateCustomer(customerId);
        customerRepository.delete(customer);
    }

    private Customer validateCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private Customer validateCustomer(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }

}
