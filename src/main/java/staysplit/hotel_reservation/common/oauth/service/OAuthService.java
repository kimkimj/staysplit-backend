package staysplit.hotel_reservation.common.oauth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Response;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.common.oauth.dto.AccessTokenDto;
import staysplit.hotel_reservation.common.oauth.dto.GoogleProfileDto;
import staysplit.hotel_reservation.common.oauth.dto.OauthSignupRequest;
import staysplit.hotel_reservation.common.oauth.dto.RedirectDto;
import staysplit.hotel_reservation.common.security.jwt.JwtTokenProvider;
import staysplit.hotel_reservation.customer.domain.dto.response.CustomerDetailsResponse;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.repository.CustomerRepository;
import staysplit.hotel_reservation.user.domain.entity.UserEntity;
import staysplit.hotel_reservation.user.domain.enums.LoginSource;
import staysplit.hotel_reservation.user.domain.enums.Role;
import staysplit.hotel_reservation.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleService googleService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;


    // oauth로 회원가입
    public CustomerDetailsResponse oauthSignup(OauthSignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
        }
        if (customerRepository.existsByNickname(request.getNickname())) {
            throw new AppException(ErrorCode.DUPLICATE_NICKNAME, ErrorCode.DUPLICATE_NICKNAME.getMessage());
        }

        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .role(Role.CUSTOMER)
                .loginSource(LoginSource.GOOGLE)
                .socialId(request.getSocialId())
                .build();
        userRepository.save(user);

        CustomerEntity customer = CustomerEntity.builder()
                .user(user)
                .name(request.getName())
                .birthdate(request.getBirthdate())
                .nickname(request.getNickname())
                .build();
        customerRepository.save(customer);

        return CustomerDetailsResponse.from(customer);
    }

    public String getGoogleUserInfo(RedirectDto redirectDto) {
        AccessTokenDto accessTokenDto = googleService.getAccessToken(redirectDto.getCode());
        GoogleProfileDto profile = googleService.getGoolgleProfile(accessTokenDto.getAccessToken());

        UserEntity user = userRepository.findBySocialId(profile.getSub())
                .orElseThrow(() -> new AppException(ErrorCode.ADDITIONAL_INFO_REQUIRED,
                        "socialId: " + profile.getSub() + ", email: " + profile.getEmail()));

        return jwtTokenProvider.createToken(user.getEmail(), user.getRole().toString());
    }

//
//    // 카카오 로그인
//    public String kakaoLogin(RedirectDto redirectDto) {
//        AccessTokenDto accessTokenDto = kakaoService.getAccessToken(redirectDto.getCode());
//        KakaoProfileDto kakaoProfileDto = kakaoService.getKakaoProfile(accessTokenDto.getAccessToken());
//
//        // 회원가입이 되어 있지 않다면 추가 정보 입력 받고, 회원가입 (oauthSignup())
//        CustomerEntity customer = customerRepository.findBySocialId(kakaoProfileDto.getId());
//        if (customer == null) {
//            throw new AppException(ErrorCode.ADDITIONAL_INFO_REQUIRED, ErrorCode.ADDITIONAL_INFO_REQUIRED.getMessage());
//        }
//
//        // 회원가입 된 회원은 jwt 토큰 발급
//        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
//    }
}