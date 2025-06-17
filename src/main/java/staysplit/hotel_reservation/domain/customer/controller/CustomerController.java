package staysplit.hotel_reservation.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.domain.common.auth.dto.OauthSignupRequest;
import staysplit.hotel_reservation.domain.common.auth.dto.RedirectDto;
import staysplit.hotel_reservation.domain.common.auth.service.GoogleService;
import staysplit.hotel_reservation.domain.common.exception.AppException;
import staysplit.hotel_reservation.domain.common.exception.ErrorCode;
import staysplit.hotel_reservation.domain.customer.domain.dto.*;
import staysplit.hotel_reservation.domain.common.entity.Response;
import staysplit.hotel_reservation.domain.customer.domain.Customer;
import staysplit.hotel_reservation.domain.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final GoogleService googleService;

    // 일반 회원가입
    @PostMapping("/sign-up")
    public Response<CustomerInfoResponse> signup(@RequestBody CustomerCreateRequest customerCreateRequest) {
        Customer customer = customerService.signup(customerCreateRequest);
        CustomerInfoResponse response = CustomerInfoResponse.from(customer);
        return staysplit.hotel_reservation.domain.common.entity.Response.success(response);
    }

    // 일반 로그인
    @PostMapping("/login")
    public Response<String> login(@RequestBody CustomerLoginRequest loginRequest) {
        String jwt = customerService.login(loginRequest);
        return Response.success(jwt);
    }

    // 구글 로그인
    @PostMapping("/google/login")
    public Response<?> googleLogin(@RequestBody RedirectDto redirectDto) {
        try {
            // 기존 사용자라면 jwt 반환
            String jwt = customerService.googleLogin(redirectDto);
            return Response.success(jwt);
        } catch (AppException e) {
            // 신규 사용자라면 추가 정보 입력 필요
            if (e.getErrorCode() == ErrorCode.ADDITIONAL_INFO_REQUIRED) {
                return Response.error(ErrorCode.ADDITIONAL_INFO_REQUIRED);
            }
            throw e; //다른 에러는 그대로 전파
        }
    }

    // Oauth 추가 정보 입력 후 회원가입
    @PostMapping("/signup/oauth")
    public Response<CustomerInfoResponse> oauthSignup(@RequestBody OauthSignupRequest request) {
        CustomerInfoResponse response = customerService.oauthSignup(
                request.getSocialId(),
                request.getEmail(),
                request.getName(),
                request.getBirthdate(),
                request.getNickname()
        );
        return Response.success(response);
    }


    @GetMapping("/{id}")
    public Response<CustomerInfoResponse> getUserDetails(@PathVariable Long id) {
        CustomerInfoResponse customerInfoResponse = customerService.findCustomerById(id);
        return Response.success(customerInfoResponse);
    }

    @PutMapping("/nickname")
    public Response<CustomerInfoResponse> changeNickname(@RequestBody NicknameChangeRequest request, Authentication authentication) {
        CustomerInfoResponse customerInfoResponse = customerService.changeNickname(request, authentication.getName());
        return Response.success(customerInfoResponse);
    }

    @PutMapping("/email")
    public Response<CustomerInfoResponse> changeEmail(@RequestBody EmailChangeRequest request, Authentication authentication) {
        CustomerInfoResponse customerInfoResponse = customerService.changeEmail(request, authentication.getName());
        return Response.success(customerInfoResponse);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return Response.success(null);
    }

}
