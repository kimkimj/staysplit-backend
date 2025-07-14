package staysplit.hotel_reservation.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import staysplit.hotel_reservation.cart.dto.request.CheckOutRequest;
import staysplit.hotel_reservation.cart.dto.response.CheckOutCartResponse;
import staysplit.hotel_reservation.cart.service.CheckOutService;
import staysplit.hotel_reservation.common.entity.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckOutService checkOutService;

    @PostMapping
    public Response<CheckOutCartResponse> checkOut(@RequestBody CheckOutRequest request, Authentication authentication) {
        CheckOutCartResponse checkOutCartResponse = checkOutService.checkOut(authentication.getName(), request);
        return Response.success(checkOutCartResponse);
    }
}
