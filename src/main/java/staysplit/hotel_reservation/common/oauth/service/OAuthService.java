package staysplit.hotel_reservation.common.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.common.security.jwt.JwtTokenProvider;
import staysplit.hotel_reservation.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final GoogleService googleService;
    private final KakaoService kakaoService;

    private final UserRepository userRepository;


    // oauth 로그인



    // 일반 회원가입
//    public UserInfoResponse signup(UserSignupRequest userCreateRequest) {
//
//        if (userRepository.existsByEmail(userCreateRequest.email())) {
//            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
//        }
//
//        UserEntity user = UserEntity.builder()
//                .email(userCreateRequest.email())
//                .password(encoder.encode(userCreateRequest.password()))
//                .build();
//
//        if (userCreateRequest.role().equals("provider")) {
//            user.setRole(Role.PROVIDER);
//        } else if (userCreateRequest.role().equals("customer")) {
//            user.setRole(Role.CUSTOMER);
//        }
//        userRepository.save(user);
//    }
//
//    // oauth로 회원가입
//    public CustomerInfoResponse oauthSignup(String socialId, String loginSource, String email,
//                                            String name, LocalDate birthdate, String nickname) {
//
//        if (userRepository.existsByEmail(email)) {
//            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
//        }
//
//        if (userRepository.existsBySocialId(socialId)) {
//            throw new AppException(ErrorCode.DUPLICATE_SOCIAL_ID, ErrorCode.DUPLICATE_SOCIAL_ID.getMessage());
//        }
//
//        CustomerEntity customer = CustomerEntity.builder()
//                .email(email)
//                .role(Role.CUSTOMER)
//                .name(name)
//                .birthdate(birthdate)
//                .nickname(nickname)
//                .socialId(socialId) // no password
//                .build();
//
//        customerRepository.save(customer);
//        return CustomerInfoResponse.from(customer);
//    }
//
//    // 일반 로그인
//    public String login(CustomerLoginRequest loginRequest) {
//        CustomerEntity customer = validateCustomer(loginRequest.email());
//        if (!encoder.matches(loginRequest.password(), customer.getPassword())) {
//            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
//        }
//        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
//    }
//
//    // 구글 로그인
//    public String googleLogin(RedirectDto redirectDto) {
//        AccessTokenDto accessTokenDto = googleService.getAccessToken(redirectDto.getCode());
//        GoogleProfileDto googleProfileDto = googleService.getGoolgleProfile(accessTokenDto.getAccessToken());
//
//        // 회원가입이 되어 있지 않다면 회원가입
//        CustomerEntity customer = customerRepository.findBySocialId(googleProfileDto.getSub());
//        if (customer == null) {
//            // 추가 정보 입력 받기
//            throw new AppException(ErrorCode.ADDITIONAL_INFO_REQUIRED, ErrorCode.ADDITIONAL_INFO_REQUIRED.getMessage());
//        }
//
//        // 회원가입 된 회원은 jwt 토큰 발급
//        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
//    }
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