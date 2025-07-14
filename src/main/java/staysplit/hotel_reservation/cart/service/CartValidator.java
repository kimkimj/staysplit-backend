package staysplit.hotel_reservation.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.cart.domain.CartEntity;
import staysplit.hotel_reservation.cart.domain.CartItemEntity;
import staysplit.hotel_reservation.cart.repository.CartItemRepository;
import staysplit.hotel_reservation.cart.repository.CartRepository;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.repository.RoomRepository;

@Component
@RequiredArgsConstructor
public class CartValidator {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RoomRepository roomRepository;


    public RoomEntity validateRoomById(Integer roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, ErrorCode.ROOM_NOT_FOUND.getMessage()));
    }

    public CartItemEntity validateCartItemById(Integer cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND, ErrorCode.CART_ITEM_NOT_FOUND.getMessage()));
    }


    public CartEntity validateCartByCustomer(CustomerEntity customer) {
        return cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND, ErrorCode.CART_NOT_FOUND.getMessage()));
    }

    private void hasAuthorityOrThrowException(CartEntity cart, CustomerEntity customer) {
        if (!cart.getCustomer().getId().equals(customer.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_CUSTOMER, ErrorCode.UNAUTHORIZED_CUSTOMER.getMessage());
        }
    }
}