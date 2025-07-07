package staysplit.hotel_reservation.provider.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;

@Component
@RequiredArgsConstructor
public class ProviderValidator {

    private final ProviderRepository providerRepository;

    public ProviderEntity validateProvider(String email) {
        return providerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }
}
