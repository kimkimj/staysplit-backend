package staysplit.hotel_reservation.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import staysplit.hotel_reservation.user.service.CustomUserDetailsService;

import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private final CustomUserDetailsService userDetailsService;

    private static final Set<String> WHITELIST = Set.of(
            "/api/customers/google/login",
            "/api/customers/google/callback",
            "/api/customers/oauth/signup",
            "/api/customers/sign-up",
            "/api/providers/sign-up",
            "/api/users/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        log.info(">>> Incoming request path: {}", path);

        // JWT 검사 예외 경로 (콜백 URL 등)
        if (WHITELIST.contains(path)) {
            log.info(">>> Whitelisted URL detected, skipping JWT filter: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                }
            }
        }

        if (jwtToken == null) {
            log.warn("JWT 토큰이 없습니다. 인증 실패");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: JWT token is missing");
            return;
        }

        try {
            if (jwtToken != null) {

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                String email = claims.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }  catch (Exception e) {
            log.error("JWT 토큰 인증 실패", e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
