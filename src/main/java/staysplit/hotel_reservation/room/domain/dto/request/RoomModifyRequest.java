package staysplit.hotel_reservation.room.domain.dto.request;


public record RoomModifyRequest(
        Long roomId,
        String photoUrl,
        String description,
        String roomType,
        Integer price,
        Integer maxOccupancy
) {
}
