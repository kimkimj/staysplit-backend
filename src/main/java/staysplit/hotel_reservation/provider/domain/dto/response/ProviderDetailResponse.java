package staysplit.hotel_reservation.provider.domain.dto.response;

import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

public record ProviderDetailResponse(
        String email,
        String hotelName
) {
    public static ProviderDetailResponse from(ProviderEntity provider) {
        String hotelName = "아직 호텔이 등록되지 않았습니다.";
        if (provider.getHotel() != null) {
            hotelName = provider.getHotel().getName();
        }

        return new ProviderDetailResponse(
                provider.getUser().getEmail(),
                hotelName
        );
    }
}

