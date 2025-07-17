package staysplit.hotel_reservation.hotel.dto.request;

public record HotelSearchRequest(
        String location,
        String checkInDate,
        String checkOutDate,
        Integer guestCount,
        String sortBy,
        Double lat,
        Double lon
) {
}
