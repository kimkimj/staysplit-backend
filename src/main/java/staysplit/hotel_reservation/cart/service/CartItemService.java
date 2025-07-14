package staysplit.hotel_reservation.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.cart.domain.CartEntity;
import staysplit.hotel_reservation.cart.domain.CartItemEntity;
import staysplit.hotel_reservation.cart.dto.request.CreateCartItemRequest;
import staysplit.hotel_reservation.cart.dto.response.CartItemDetailResponse;
import staysplit.hotel_reservation.cart.mapper.CartMapper;
import staysplit.hotel_reservation.cart.repository.CartItemRepository;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.service.CustomerValidator;
import staysplit.hotel_reservation.reservedRoom.service.RoomStockService;
import staysplit.hotel_reservation.room.domain.RoomEntity;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartValidator cartValidator;
    private final CustomerValidator customerValidator;
    private final RoomStockService roomStockService;
    private final CartMapper cartMapper;

    // 카트에 있는 방 수량 변경
    public CartItemDetailResponse modifyQuantity(Integer cartItemId, int delta, String email) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartItemEntity cartItem = cartValidator.validateCartItemById(cartItemId);

        int updated = cartItem.getQuantity() + delta;

        if (updated < 0) {
            throw new IllegalStateException("수량이 음수가 될 수 없습니다");
        }

        if (updated == 0) {
            cartItemRepository.delete(cartItem);
            return null;
        }

        cartItem.changeQuantity(updated);
        return cartMapper.toCartItemDetailResponse(cartItem);
    }

    // 카트에서 방 삭제하기
    public void deleteCartItem(String email, Integer cartItemId) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartEntity cart = cartValidator.validateCartByCustomer(customer);
        CartItemEntity cartItem = cartValidator.validateCartItemById(cartItemId);
        cartItemRepository.delete(cartItem);
    }

    // 카트에 추가하거나 수량 올리기
    public CartItemDetailResponse addToCart(CreateCartItemRequest request, String email) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartEntity cart = cartValidator.validateCartByCustomer(customer);
        RoomEntity room = cartValidator.validateRoomById(request.roomId());

        // 방이 이미 있으면 에러
        Optional<CartItemEntity> existingCartItem = cartItemRepository
                .findByCartAndRoomAndCheckInDateAndCheckOutDate(cart, room, request.checkInDate(), request.checkOutDate());

        if (existingCartItem.isPresent()) {
            throw new AppException(ErrorCode.ITEM_ALREADY_IN_CART, ErrorCode.ITEM_ALREADY_IN_CART.getMessage());
        }

        // 이 방이 지정된 체크아웃과 체크인 날자 동안 재고가 있는지 확인
        int availableStock = roomStockService.validateAvailableStock(room, request.checkInDate(), request.checkOutDate(), request.quantity());

        // 요청된 수량과 같거나 많으면 허락
        CartItemEntity cartItem = CartItemEntity.builder()
                .cart(cart)
                .room(room)
                .quantity(request.quantity())
                .checkInDate(request.checkInDate())
                .checkOutDate(request.checkOutDate())
                .pricePerNight(room.getPrice())
                .subTotal(request.quantity() * room.getPrice())
                .build();

        cartItemRepository.save(cartItem);
        return cartMapper.toCartItemDetailResponse(cartItem);
    }

    @Transactional(readOnly = true)
    public CartItemDetailResponse getCartItem(Integer cartItemId, String email) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartItemEntity cartItem = cartValidator.validateCartItemById(cartItemId);
        int availableStock = roomStockService.validateAvailableStock(
                cartItem.getRoom(), cartItem.getCheckInDate(), cartItem.getCheckOutDate(), cartItem.getQuantity());
        return cartMapper.toCartItemDetailResponse(cartItem);
    }




}