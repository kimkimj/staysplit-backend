package staysplit.hotel_reservation.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.domain.customer.domain.dto.*;
import staysplit.hotel_reservation.domain.common.entity.Response;
import staysplit.hotel_reservation.domain.customer.domain.Customer;
import staysplit.hotel_reservation.domain.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/sign-up")
    public staysplit.hotel_reservation.domain.common.entity.Response<CustomerInfoResponse> signup(@RequestBody CustomerCreateRequest customerCreateRequest) {
        Customer customer = customerService.signup(customerCreateRequest);
        CustomerInfoResponse response = CustomerInfoResponse.from(customer);
        return staysplit.hotel_reservation.domain.common.entity.Response.success(response);
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody CustomerLoginRequest loginRequest) {
        String jwt = customerService.login(loginRequest);
        return Response.success(jwt);
    }

    @GetMapping("/{id}")
    public Response<CustomerInfoResponse> getUserDetails(@PathVariable Long id) {
        CustomerInfoResponse customerInfoResponse = customerService.findCustomerById(id);
        return Response.success(customerInfoResponse);
    }

    @PutMapping("/nickname")
    public Response<CustomerInfoResponse> changeNickname(@RequestBody NicknameChangeRequest request, Authentication authentication) {
        CustomerInfoResponse customerInfoResponse = customerService.changeNickname(request, authentication.getName());
        return Response.success(customerInfoResponse);
    }

    @PutMapping("/email")
    public Response<CustomerInfoResponse> changeEmail(@RequestBody EmailChangeRequest request, Authentication authentication) {
        CustomerInfoResponse customerInfoResponse = customerService.changeEmail(request, authentication.getName());
        return Response.success(customerInfoResponse);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return Response.success(null);
    }

}
