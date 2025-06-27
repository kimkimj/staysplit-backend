package staysplit.hotel_reservation.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class CustomerValidator {
    private final CustomerRepository customerRepository;

    public CustomerEntity validateCustomerById(String email) {
        return customerRepository.findByUserByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }

}
