package staysplit.hotel_reservation.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.customer.domain.dto.response.CustomerInfoResponse;
import staysplit.hotel_reservation.provider.service.ProviderService;
import staysplit.hotel_reservation.user.domain.dto.request.LoginRequest;
import staysplit.hotel_reservation.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ProviderService providerService;


    // 일반 로그인, 소셜 로그인 아님
    @PostMapping("/login")
    public Response<String> login(@RequestBody LoginRequest loginRequest) {
        String jwt = userService.login(loginRequest);
        return Response.success(jwt);
    }


    // 사용자 삭제
}
