package staysplit.hotel_reservation.cart.dto.request;

import java.time.LocalDate;

public record CreateCartItemRequest (
        Integer roomId,
        Integer quantity,
        LocalDate checkInDate,
        LocalDate checkOutDate
){
}