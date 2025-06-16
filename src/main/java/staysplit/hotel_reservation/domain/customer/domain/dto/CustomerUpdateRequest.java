package staysplit.hotel_reservation.domain.customer.domain.dto;


import java.time.LocalDate;

public record CustomerUpdateRequest(

        String email,

        String name,
        LocalDate birthdate,
        String nickname
) {}