package staysplit.hotel_reservation.hotel.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHotelListRequest {
    private Long hotelId;
}
