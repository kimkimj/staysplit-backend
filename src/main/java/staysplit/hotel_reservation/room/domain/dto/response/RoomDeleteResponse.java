package staysplit.hotel_reservation.room.domain.dto.response;

public record RoomDeleteResponse(
        Long hotelId,
        Long roomId
) {
}
