package staysplit.hotel_reservation.customer.domain.dto.response;

import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;

import java.time.LocalDate;

public record CustomerInfoResponse(
        Long id,
        String email,
        String name,
        LocalDate birthdate,
        String nickname) {

    public static CustomerInfoResponse from(CustomerEntity customer) {
        return new CustomerInfoResponse(
                customer.getId(),
                customer.getUser().getEmail(),
                customer.getName(),
                customer.getBirthdate(),
                customer.getNickname()
        );
    }
}

