package staysplit.hotel_reservation.user.domain.dto.response;

public record UserLoginStatusResponse(
        String email,
        String role,
        Boolean loggedIn
) {
}
