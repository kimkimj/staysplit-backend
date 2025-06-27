package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.domain.entity.PhotoEntity;

import java.math.BigDecimal;
import java.util.List;

public record GetHotelDetailResponse(
        Long hotelId,
        String name,
        String address,
        BigDecimal longitude,
        BigDecimal latitude,
        String description,
        Integer starLevel,
        double rating,
        Integer reviewCount,
        String mainImagePath,
        List<String> hotelImagePaths
) {

    public static GetHotelDetailResponse toDto(HotelEntity hotel) {

        String mainImagePath = hotel.getMainPhoto()
                .map(PhotoEntity::getStoredFileName)
                .orElse(null);

        return new GetHotelDetailResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getLongitude(),
                hotel.getLatitude(),
                hotel.getDescription(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                mainImagePath,
                hotel.getHotelPhotos().stream()
                        .map(PhotoEntity::getStoredFileName)
                        .toList());
    }
}
