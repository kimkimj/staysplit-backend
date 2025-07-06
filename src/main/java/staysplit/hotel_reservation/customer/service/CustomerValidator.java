package staysplit.hotel_reservation.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerValidator {
    private final CustomerRepository customerRepository;

    public CustomerEntity validateCustomerByEmail(String email) {
        return customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    public List<String> validateCustomerEmailList(List<String> emails) {
        List<String> list = new ArrayList<>();
        for (String email: emails) {
            CustomerEntity customer = validateCustomerByEmail(email);
            list.add(email);
        }
        return list;
    }
}
