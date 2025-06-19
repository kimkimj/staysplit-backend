package staysplit.hotel_reservation.hotel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteHotelRequest {
    private Long hotelId;
    private Long providerId;
}
