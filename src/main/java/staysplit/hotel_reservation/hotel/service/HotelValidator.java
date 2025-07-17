package staysplit.hotel_reservation.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;

@Component
@RequiredArgsConstructor
public class HotelValidator {
    private final HotelRepository hotelRepository;

    public HotelEntity validateHotel(Integer hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new AppException(ErrorCode.HOTEL_NOT_FOUND, ErrorCode.HOTEL_NOT_FOUND.getMessage()));
    }

    public boolean hasAuthorityToHotel(Integer providerId, HotelEntity hotel) {
        if (hotel.getProvider().getId().equals(providerId)) {
            return true;
        }
        throw new AppException(ErrorCode.UNAUTHORIZED_PROVIDER, ErrorCode.UNAUTHORIZED_PROVIDER.getMessage());
    }
}
