package staysplit.hotel_reservation.cart.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.cart.domain.CartEntity;
import staysplit.hotel_reservation.cart.domain.CartItemEntity;
import staysplit.hotel_reservation.cart.dto.response.CartDetailResponse;
import staysplit.hotel_reservation.cart.dto.response.CartItemDetailResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    public CartDetailResponse toDetailResponse(CartEntity cart) {

        List<CartItemDetailResponse> items = cart.getCartItemList().stream()
                .map(this::toCartItemDetailResponse)
                .toList();

        int totalPrice = cart.getCartItemList().stream()
                .mapToInt(item -> item.getSubTotal())
                .sum();

        return new CartDetailResponse(
                cart.getId(),
                cart.getCustomer().getId(),
                items,
                totalPrice
        );
    }

    public CartItemDetailResponse toCartItemDetailResponse(CartItemEntity cartItem) {
        return new CartItemDetailResponse(
                cartItem.getCart().getId(),
                cartItem.getRoom().getHotel().getId(),
                cartItem.getRoom().getId(),
                cartItem.getCheckInDate(),
                cartItem.getCheckOutDate(),
                cartItem.getQuantity(),
                cartItem.getPricePerNight(),
                cartItem.getSubTotal());
    }

}
