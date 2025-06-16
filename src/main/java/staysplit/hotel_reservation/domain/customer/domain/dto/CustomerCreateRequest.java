package staysplit.hotel_reservation.domain.customer.domain.dto;

import java.time.LocalDate;

// TODO: validation
public record CustomerCreateRequest(

        String email,
        String password,

        String name,
        LocalDate birthdate,
        String nickname
) {}

