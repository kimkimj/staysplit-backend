package staysplit.hotel_reservation.hotel.dto.request;

public record UpdateHotelRequest(
        Long hotelId,
        Long providerId,
        String name,
        String address,
        String description,
        Integer starLevel,
        Double rating,
        String imageUrl
) {}
