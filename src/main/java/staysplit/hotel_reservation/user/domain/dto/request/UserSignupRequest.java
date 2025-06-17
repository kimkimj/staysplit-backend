package staysplit.hotel_reservation.user.domain.dto.request;

import java.time.LocalDate;

public record UserSignupRequest(

        String role,
        String email,
        String password,

        String name,
        LocalDate birthdate,
        String nickname

) { }
