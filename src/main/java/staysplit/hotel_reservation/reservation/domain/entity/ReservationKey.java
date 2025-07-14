package staysplit.hotel_reservation.reservation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ReservationKey {
    private Integer hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
