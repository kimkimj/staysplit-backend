package staysplit.hotel_reservation.hotel.dto.request;

public record HotelSearchRequest(
        String checkInDate,
        String checkOutDate,
        Integer guestCount,
        Double lat,
        Double lon
) {
}
