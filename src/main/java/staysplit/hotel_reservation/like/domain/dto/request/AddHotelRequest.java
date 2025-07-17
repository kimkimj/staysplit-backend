package staysplit.hotel_reservation.like.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddHotelRequest {
    private Integer hotelId;
    private Integer customerId;
}
