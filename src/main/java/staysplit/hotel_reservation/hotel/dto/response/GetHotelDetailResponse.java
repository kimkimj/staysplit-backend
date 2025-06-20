package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;

import java.math.BigDecimal;


public record GetHotelDetailResponse(
        Long hotelId,
        String name,
        String address,
        BigDecimal longtitude,
        BigDecimal latitude,
        String description,
        Integer starLevel,
        double rating,
        Integer reviewCount,
        String imageUrl
) {
    public static GetHotelDetailResponse toDto(HotelEntity hotel) {
        return new GetHotelDetailResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getLongtitude(),
                hotel.getLatitude(),
                hotel.getDescription(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                hotel.getImageUrl()
        );
    }
}
