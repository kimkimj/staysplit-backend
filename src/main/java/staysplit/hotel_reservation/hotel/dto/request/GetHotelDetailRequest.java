package staysplit.hotel_reservation.hotel.dto.request;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

import java.math.BigDecimal;

@Getter
public class GetHotelDetailRequest {
    private Long hotelId;
}
