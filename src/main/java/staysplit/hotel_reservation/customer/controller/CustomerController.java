package staysplit.hotel_reservation.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.oauth.service.GoogleService;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.customer.domain.dto.request.NicknameChangeRequest;
import staysplit.hotel_reservation.customer.domain.dto.request.CustomerSignupRequest;
import staysplit.hotel_reservation.customer.domain.dto.response.CustomerDetailsResponse;
import staysplit.hotel_reservation.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final GoogleService googleService;

    // 일반 회원가입
    @PostMapping("/sign-up")
    public Response<CustomerDetailsResponse> signup(@RequestBody CustomerSignupRequest request) {
        CustomerDetailsResponse customerDetailsResponse = customerService.signup(request);
        return Response.success(customerDetailsResponse);
    }

    // 사용자 프로필 조회
    @GetMapping("/my")
    public Response<CustomerDetailsResponse> getMyInfo(Authentication authentication) {
        CustomerDetailsResponse customerDetailsResponse = customerService.getMyProfile(authentication.getName());
        return Response.success(customerDetailsResponse);
    }

    // 사용자 정보 조회 - Admin
    @GetMapping("/{id}")
    public Response<CustomerDetailsResponse> getUserDetails(@PathVariable Long id) {
        CustomerDetailsResponse customerDetailsResponse = customerService.findCustomerById(id);
        return Response.success(customerDetailsResponse);
    }

    // 사용자 이름 수정
    @PutMapping("/nickname")
    public Response<CustomerDetailsResponse> changeNickname(@RequestBody NicknameChangeRequest request, Authentication authentication) {
        CustomerDetailsResponse customerDetailsResponse = customerService.changeNickname(request, authentication.getName());
        return Response.success(customerDetailsResponse);
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


    @GetMapping("/all")
    public Response<Page<CustomerDetailsResponse>> findAllCustomers(Pageable pageable) {
        Page<CustomerDetailsResponse> allCustomers = customerService.getAllCustomers(pageable);
        return Response.success(allCustomers);
    }
}

