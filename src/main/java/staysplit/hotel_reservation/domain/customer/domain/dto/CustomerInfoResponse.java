package staysplit.hotel_reservation.domain.customer.domain.dto;

import staysplit.hotel_reservation.domain.customer.domain.Customer;

import java.time.LocalDate;

public record CustomerInfoResponse(
        Long id,
        String email,
        String name,
        LocalDate birthdate,
        String nickname) {

    public static CustomerInfoResponse from(Customer customer) {
        return new CustomerInfoResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getBirthdate(),
                customer.getNickname()
        );
    }
}

