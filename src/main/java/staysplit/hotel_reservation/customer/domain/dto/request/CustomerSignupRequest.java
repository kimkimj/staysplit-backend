package staysplit.hotel_reservation.customer.domain.dto.request;

import java.time.LocalDate;

// TODO: validation
public record CustomerSignupRequest(

        String email,
        String password,

        String name,
        LocalDate birthdate,
        String nickname
) {}

