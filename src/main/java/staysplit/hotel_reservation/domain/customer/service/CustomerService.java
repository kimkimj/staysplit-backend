package staysplit.hotel_reservation.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.domain.common.auth.JwtTokenProvider;
import staysplit.hotel_reservation.domain.common.entity.Response;
import staysplit.hotel_reservation.domain.customer.domain.dto.*;
import staysplit.hotel_reservation.domain.common.exception.AppException;
import staysplit.hotel_reservation.domain.common.exception.ErrorCode;
import staysplit.hotel_reservation.domain.customer.domain.Customer;
import staysplit.hotel_reservation.domain.customer.domain.Role;
import staysplit.hotel_reservation.domain.customer.repository.CustomerRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Customer signup(CustomerCreateRequest customerCreateRequest) {

        if (customerRepository.existsByEmail(customerCreateRequest.email())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
        }

        Customer customer = Customer.builder()
                .email(customerCreateRequest.email())
                .password(encoder.encode(customerCreateRequest.password()))
                .role(Role.CUSTOMER)
                .name(customerCreateRequest.name())
                .birthdate(customerCreateRequest.birthdate())
                .nickname(customerCreateRequest.nickname())
                .build();

        customerRepository.save(customer);
        return customer;
    }

    public String login(CustomerLoginRequest loginRequest) {
        Customer customer = validateCustomer(loginRequest.email());
        if (!encoder.matches(loginRequest.password(), customer.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }
        return jwtTokenProvider.createToken(customer.getEmail(), customer.getRole().toString());
    }


    public CustomerInfoResponse findCustomerById(Long id) {
        Customer customer = validateCustomer(id);
        return CustomerInfoResponse.from(customer);
    }

    public CustomerInfoResponse changeNickname(NicknameChangeRequest request, String email) {
        Customer customer = validateCustomer(email);
        if (customerRepository.existsByNickname(request.getNickname())) {
            throw new AppException(ErrorCode.DUPLICATE_NICKNAME, ErrorCode.DUPLICATE_NICKNAME.getMessage());
        }
        customer.setNickname(request.getNickname());
        return CustomerInfoResponse.from(customer);
    }

    // 두번째 이메일은 Authentication 객체에 있는 값
    public CustomerInfoResponse changeEmail(EmailChangeRequest emailChangeRequest, String email) {
        Customer customer = validateCustomer(email);
        if (customerRepository.existsByEmail(emailChangeRequest.getEmail())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL, ErrorCode.DUPLICATE_EMAIL.getMessage());
        }
        customer.changeEmail(emailChangeRequest.getEmail());
        return CustomerInfoResponse.from(customer);
    }

    public void delete(Long customerId) {
        Customer customer = validateCustomer(customerId);
        customerRepository.delete(customer);
    }

    private Customer validateCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private Customer validateCustomer(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }
}
