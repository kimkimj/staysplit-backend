package staysplit.hotel_reservation.provider.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.provider.domain.dto.reqeust.ProviderSignupRequest;
import staysplit.hotel_reservation.provider.domain.dto.response.ProviderDetailResponse;
import staysplit.hotel_reservation.provider.domain.dto.response.ProviderSignupResponse;
import staysplit.hotel_reservation.provider.service.ProviderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService providerService;

    // 회원가입
    @PostMapping("/sign-up")
    public Response<ProviderSignupResponse> signupAsProvider(@RequestBody ProviderSignupRequest request) {
        ProviderSignupResponse response = providerService.signup(request);
        return Response.success(response);
    }

    // 마이페이지
    @GetMapping("/my")
    public Response<ProviderDetailResponse> getProviderInfo(Authentication authentication) {
        ProviderDetailResponse myPage = providerService.getMyPage(authentication.getName());
        return Response.success(myPage);
    }
}
