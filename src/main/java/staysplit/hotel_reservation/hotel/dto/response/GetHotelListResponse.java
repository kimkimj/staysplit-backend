package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.service.PhotoUrlBuilder;

public record GetHotelListResponse(
        Integer hotelId,
        String name,
        String address,
        Integer starLevel,
        Double rating,
        Integer reviewCount,
        String mainImageUrl
) {
    public static GetHotelListResponse toDto(HotelEntity hotel, PhotoUrlBuilder photoUrlBuilder) {
        return new GetHotelListResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                hotel.getMainPhoto().map(photo -> photo.buildFullUrl(photoUrlBuilder)).orElse(null)
        );
    }
}
