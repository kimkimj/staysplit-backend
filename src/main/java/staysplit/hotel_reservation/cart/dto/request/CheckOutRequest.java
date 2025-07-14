package staysplit.hotel_reservation.cart.dto.request;

import java.util.List;

public record CheckOutRequest(
        List<Integer> cartItemIds,
        boolean isSplitPayment,
        List<String> invitedEmails
) {
}
