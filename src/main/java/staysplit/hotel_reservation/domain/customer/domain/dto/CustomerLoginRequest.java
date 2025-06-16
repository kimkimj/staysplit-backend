package staysplit.hotel_reservation.domain.customer.domain.dto;


public record CustomerLoginRequest (
        String email,
        String password
) {}
