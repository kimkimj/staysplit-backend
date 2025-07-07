package staysplit.hotel_reservation.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/logout")
    public Response<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return Response.success("로그아웃 성공");
    }
}
