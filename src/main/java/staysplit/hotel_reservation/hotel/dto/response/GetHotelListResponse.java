package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.domain.entity.PhotoEntity;

public record GetHotelListResponse(
        Long hotelId,
        String name,
        String address,
        Integer starLevel,
        double rating,
        Integer reviewCount,
        String mainImagePath
) {
    public static GetHotelListResponse toDto(HotelEntity hotel) {

        String mainImagePath = hotel.getMainPhoto()
                .map(PhotoEntity::getStoredFileName)
                .orElse(null);

        return new GetHotelListResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                mainImagePath
        );
    }
}
