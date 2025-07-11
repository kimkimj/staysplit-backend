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
    public static GetHotelListResponse from(HotelEntity hotel, PhotoUrlBuilder urlBuilder) {
        String mainUrl = hotel.getMainPhoto() != null ? hotel.getMainPhoto().buildFullUrl(urlBuilder) : null;

        return new GetHotelListResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                mainUrl
        );
    }
}
