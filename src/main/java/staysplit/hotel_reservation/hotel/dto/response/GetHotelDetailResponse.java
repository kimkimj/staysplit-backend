package staysplit.hotel_reservation.hotel.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record GetHotelDetailResponse(
        Integer hotelId,
        String name,
        String address,
        BigDecimal longitude,
        BigDecimal latitude,
        String description,
        Integer starLevel,
        Double rating,
        Integer reviewCount,
        String mainPhotoUrl,
        List<String> additionalPhotoUrls
) {

}