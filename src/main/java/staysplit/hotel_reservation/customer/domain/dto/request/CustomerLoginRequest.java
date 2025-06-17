package staysplit.hotel_reservation.customer.domain.dto.request;


public record CustomerLoginRequest (
        String email,
        String password
) {}
