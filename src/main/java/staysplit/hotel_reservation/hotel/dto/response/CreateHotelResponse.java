package staysplit.hotel_reservation.hotel.dto.response;

public record CreateHotelResponse(
        Integer hotelId,
        String name,
        String address,
        String description,
        Integer starLevel
) {
}