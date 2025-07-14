package staysplit.hotel_reservation.cart.dto.response;

import staysplit.hotel_reservation.cart.domain.CartItemEntity;

import java.time.LocalDate;

public record CartItemDetailResponse(
        Integer cartId,
        Integer hotelId,
        Integer roomId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer quantity,
        Integer pricePerNight,
        Integer subTotal
) {

}