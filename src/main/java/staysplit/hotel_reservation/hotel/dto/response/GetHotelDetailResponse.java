package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.domain.dto.response.PhotoDetailResponse;

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
        List<PhotoDetailResponse> hotelPhotos
) {
    public static GetHotelDetailResponse toDto(HotelEntity hotel) {
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
                hotel.getHotelPhotos().stream()
                        .map(PhotoDetailResponse::from)
                        .toList()
        );
    }
}
