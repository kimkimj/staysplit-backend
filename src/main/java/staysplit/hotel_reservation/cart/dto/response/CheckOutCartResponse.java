package staysplit.hotel_reservation.cart.dto.response;

import staysplit.hotel_reservation.reservation.dto.response.ReservationDetailResponse;

import java.util.List;

public record CheckOutCartResponse(
        List<ReservationDetailResponse> reservations,
        Integer totalPrice,
        boolean isSplitPayment,
        Integer splitAmount
) {
}
