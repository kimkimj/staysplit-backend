package staysplit.hotel_reservation.user.domain.dto.response;

public record UserLoginResponse(
        String jwt,
        String role
) {
}
