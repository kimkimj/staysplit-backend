package staysplit.hotel_reservation.customer.domain.dto.request;


import java.time.LocalDate;

public record UpdateCustomerRequest(

        String email,

        String name,
        LocalDate birthdate,
        String nickname
) {}