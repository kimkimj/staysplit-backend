package staysplit.hotel_reservation.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.user.domain.dto.request.LoginRequest;
import staysplit.hotel_reservation.user.domain.dto.request.PasswordUpdateRequest;
import staysplit.hotel_reservation.user.domain.dto.response.UserLoginResponse;
import staysplit.hotel_reservation.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // 일반 로그인, 소셜 로그인 아님
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserLoginResponse response = userService.login(loginRequest);
        return Response.success(response);
    }

    // 비밀 번호 변경
    @PutMapping("/pw")
    public Response<String> changePassword(@RequestBody PasswordUpdateRequest request,
                                           Authentication authentication) {
        String response = userService.changePassword(request, authentication.getName());
        return Response.success(response);
    }
}
