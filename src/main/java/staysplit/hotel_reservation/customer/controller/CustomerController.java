package staysplit.hotel_reservation.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.oauth.dto.OauthSignupRequest;
import staysplit.hotel_reservation.common.oauth.dto.RedirectDto;
import staysplit.hotel_reservation.common.oauth.service.GoogleService;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.customer.domain.dto.request.NicknameChangeRequest;
import staysplit.hotel_reservation.customer.domain.dto.request.CustomerSignupRequest;
import staysplit.hotel_reservation.customer.domain.dto.request.CustomerLoginRequest;
import staysplit.hotel_reservation.customer.domain.dto.request.EmailChangeRequest;
import staysplit.hotel_reservation.customer.domain.dto.response.CustomerInfoResponse;
import staysplit.hotel_reservation.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final GoogleService googleService;

    // 일반 회원가입
    @PostMapping("/sign-up")
    public Response<CustomerInfoResponse> signup(@RequestBody CustomerSignupRequest request) {
        CustomerInfoResponse customerInfoResponse = customerService.signup(request);
        return Response.success(customerInfoResponse);
    }

    // 사용자 프로필 조회
    @GetMapping("/my")
    public Response<CustomerInfoResponse> getMyInfo(Authentication authentication) {
        CustomerInfoResponse customerInfoResponse = customerService.getMyProfile(authentication.getName());
        return Response.success(customerInfoResponse);
    }

    // 사용자 정보 조회 - Admin
    @GetMapping("/{id}")
    public Response<CustomerInfoResponse> getUserDetails(@PathVariable Long id) {
        CustomerInfoResponse customerInfoResponse = customerService.findCustomerById(id);
        return Response.success(customerInfoResponse);
    }

    // 사용자 이름 수정
    @PutMapping("/nickname")
    public Response<CustomerInfoResponse> changeNickname(@RequestBody NicknameChangeRequest request, Authentication authentication) {
        CustomerInfoResponse customerInfoResponse = customerService.changeNickname(request, authentication.getName());
        return Response.success(customerInfoResponse);
    }

    // 이메일 변경
//    @PutMapping("/email")
//    public Response<CustomerInfoResponse> changeEmail(@RequestBody EmailChangeRequest request, Authentication authentication) {
//        CustomerInfoResponse customerInfoResponse = customerService.changeEmail(request, authentication.getName());
//        return Response.success(customerInfoResponse);
//    }

    // 내 계정 삭제
    @DeleteMapping("/my")
    public Response<String> delete(Authentication authentication) {
        customerService.delete(authentication.getName());
        return Response.success("계정이 삭제되었습니다.");
    }


//    // 구글 로그인
//    @PostMapping("/google/login")
//    public Response<?> googleLogin(@RequestBody RedirectDto redirectDto) {
//        try {
//            // 기존 사용자라면 jwt 반환
//            String jwt = customerService.googleLogin(redirectDto);
//            return Response.success(jwt);
//        } catch (AppException e) {
//            // 신규 사용자라면 추가 정보 입력 필요
//            if (e.getErrorCode() == ErrorCode.ADDITIONAL_INFO_REQUIRED) {
//                return Response.error(ErrorCode.ADDITIONAL_INFO_REQUIRED);
//            }
//            throw e; //다른 에러는 그대로 전파
//        }
//    }

//    // Oauth 추가 정보 입력 후 회원가입
//    @PostMapping("/signup/oauth")
//    public Response<CustomerInfoResponse> oauthSignup(@RequestBody OauthSignupRequest request) {
//        CustomerInfoResponse response = customerService.oauthSignup(
//                request.getSocialId(),
//                request.getEmail(),
//                request.getName(),
//                request.getBirthdate(),
//                request.getNickname()
//        );
//        return Response.success(response);
//    }

}
