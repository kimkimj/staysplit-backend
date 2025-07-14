package staysplit.hotel_reservation.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.cart.dto.request.CreateCartItemRequest;
import staysplit.hotel_reservation.cart.dto.response.CartItemDetailResponse;
import staysplit.hotel_reservation.cart.service.CartItemService;
import staysplit.hotel_reservation.common.entity.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts/items")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public Response<CartItemDetailResponse> addNewItem(@Valid @RequestBody CreateCartItemRequest request, Authentication authentication) {
        CartItemDetailResponse cartItemDetailResponse = cartItemService.addToCart(request, authentication.getName());
        return Response.success(cartItemDetailResponse);
    }

    @GetMapping("/{cartItemId}")
    public Response<CartItemDetailResponse> getCartItem(@PathVariable Integer cartItemId, Authentication authentication) {
        CartItemDetailResponse cartItem = cartItemService.getCartItem(cartItemId, authentication.getName());
        return Response.success(cartItem);
    }

    // 수량 수정
    // 음수면 줄이고 양수면 늘린다
    @PutMapping("/{cartItemId}")
    public Response<?> modifyCartItemQuantity(@PathVariable Integer cartItemId,
                                              @RequestParam Integer changedQuantity,
                                              Authentication authentication) {
        CartItemDetailResponse response = cartItemService.modifyQuantity(cartItemId, changedQuantity, authentication.getName());
        if (response == null) {
            return Response.success("장바구니에서 삭제되었습니다.");
        }
        return Response.success(response);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/{cartItemId}")
    public Response<String> deleteCartItem(@PathVariable Integer cartItemId, Authentication authentication) {
        cartItemService.deleteCartItem(authentication.getName(), cartItemId);
        return Response.success("장바구니에서 삭제되었습니다.");
    }
}