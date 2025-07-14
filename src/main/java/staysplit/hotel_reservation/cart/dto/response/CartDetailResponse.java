package staysplit.hotel_reservation.cart.dto.response;

import staysplit.hotel_reservation.cart.domain.CartEntity;

import java.util.List;

public record CartDetailResponse(
        Integer cartId,
        Integer customerId,
        List<CartItemDetailResponse> rooms,
        Integer totalPrice
) {

}