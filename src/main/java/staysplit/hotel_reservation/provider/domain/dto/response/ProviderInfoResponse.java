package staysplit.hotel_reservation.provider.domain.dto.response;

import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

public record ProviderInfoResponse(
        String email
        //String hotelName
) {
    public static ProviderInfoResponse from(ProviderEntity provider) {
        return new ProviderInfoResponse(
                provider.getUser().getEmail()
                //provider.getHotel().getName()
        );
    }
}
