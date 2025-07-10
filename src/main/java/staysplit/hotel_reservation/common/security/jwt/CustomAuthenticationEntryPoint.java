package staysplit.hotel_reservation.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
//
//        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_TOKEN, ErrorCode.INVALID_TOKEN.getMessage());
//        Response<ErrorResponse> errorBody = Response.error(errorResponse);
//        String json = objectMapper.writeValueAsString(errorBody);
//        response.getWriter().write(json);
    }
}
