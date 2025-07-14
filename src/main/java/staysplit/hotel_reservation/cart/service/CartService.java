package staysplit.hotel_reservation.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.cart.domain.CartEntity;
import staysplit.hotel_reservation.cart.dto.response.CartDetailResponse;
import staysplit.hotel_reservation.cart.mapper.CartMapper;
import staysplit.hotel_reservation.cart.repository.CartRepository;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.service.CustomerValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartValidator cartValidator;
    private final CustomerValidator customerValidator;
    private final CartMapper mapper;


    // 사용자의 장바구니 조회
    @Transactional(readOnly = true)
    public CartDetailResponse getMyCart(String email) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartEntity cart = cartValidator.validateCartByCustomer(customer);

        return mapper.toDetailResponse(cart);
    }

    // 장바구니 생성
    public void createCart(String email) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartEntity cart = new CartEntity(customer);
        cartRepository.save(cart);
    }

    // 장바구니 삭제
    public void deleteCart(String email) {
        CustomerEntity customer = customerValidator.validateCustomerByEmail(email);
        CartEntity cart = cartValidator.validateCartByCustomer(customer);
        cartRepository.delete(cart);
    }
}