package staysplit.hotel_reservation.provider.domain.dto.response;

import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

public record ProviderDetailResponse(
        String email
        //String hotelName
) {
    public static ProviderDetailResponse from(ProviderEntity provider) {
        return new ProviderDetailResponse(
                provider.getUser().getEmail()
                //provider.getHotel().getName()
        );
    }
}
