package staysplit.hotel_reservation.hotel.dto.response;

import staysplit.hotel_reservation.hotel.entity.HotelEntity;

public record UpdateHotelResponse(
        String name,
        String address,
        String description,
        Integer starLevel,
        double rating,
        String imageUrl
) {
    public static UpdateHotelResponse toDto(HotelEntity hotel) {
        return new UpdateHotelResponse(
                hotel.getName(),
                hotel.getAddress(),
                hotel.getDescription(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getImageUrl()
        );
    }
}
