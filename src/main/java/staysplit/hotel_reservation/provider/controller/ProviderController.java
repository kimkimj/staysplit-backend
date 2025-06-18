package staysplit.hotel_reservation.provider.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.provider.domain.dto.reqeust.ProviderSignupRequest;
import staysplit.hotel_reservation.provider.domain.dto.response.ProviderInfoResponse;
import staysplit.hotel_reservation.provider.service.ProviderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService providerService;

    // 회원가입
    @PostMapping("/sign-up")
    public Response<ProviderInfoResponse> signupAsProvider(@RequestBody ProviderSignupRequest request) {
        ProviderInfoResponse providerInfoResponse = providerService.signup(request);
        return Response.success(providerInfoResponse);
    }
}
