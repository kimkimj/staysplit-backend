package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.service.PhotoUrlBuilder;

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
    public static GetHotelDetailResponse from(HotelEntity hotel, PhotoUrlBuilder urlBuilder) {
        String mainUrl = hotel.getMainPhoto() != null ? hotel.getMainPhoto().buildFullUrl(urlBuilder) : null;
        List<String> additionalUrls = hotel.getAdditionalPhotos() == null ? null : hotel.getAdditionalPhotos().stream()
                .map(photo -> photo.buildFullUrl(urlBuilder))
                .toList();

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
                mainUrl,
                additionalUrls
        );
    }
}