package staysplit.hotel_reservation.reservation.dto.request;


import java.time.LocalDate;
import java.util.List;

public record CreateReservationRequest(
        List<RoomReservationDto> roomsAndQuantities,

        LocalDate checkInDate,

        LocalDate checkOutDate,
        List<String> invitedEmails,
        Boolean isSplitPayment

) {
    public record RoomReservationDto(
            Integer roomId,
            Integer quantity
    ) {}
}
