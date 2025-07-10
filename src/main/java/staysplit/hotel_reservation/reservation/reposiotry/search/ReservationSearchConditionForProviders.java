package staysplit.hotel_reservation.reservation.reposiotry.search;

import java.time.LocalDate;

public record ReservationSearchConditionForProviders(
        String reservationNumber,
        String reservationStatus,
        LocalDate checkInStart,
        LocalDate checkInEnd,
        LocalDate checkOutStart,
        LocalDate checkOutEnd,
        LocalDate createdAtStart,
        LocalDate createdAtEnd,
        String guestEmail,
        String guestName,
        String roomType
) {
}
