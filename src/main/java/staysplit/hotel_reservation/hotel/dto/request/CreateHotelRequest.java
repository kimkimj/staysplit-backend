package staysplit.hotel_reservation.hotel.dto.request;

public record CreateHotelRequest(
        Long providerId,
        String name,
        String address,
        String description,
        Integer starLevel,
        Double rating,
        String imageUrl
) {}
