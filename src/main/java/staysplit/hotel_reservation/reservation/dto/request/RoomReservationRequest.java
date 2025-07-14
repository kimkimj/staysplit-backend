package staysplit.hotel_reservation.reservation.dto.request;

public record RoomReservationRequest(
        Integer roomId,
        Integer quantity
) {
}
