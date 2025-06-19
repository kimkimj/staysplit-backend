package staysplit.hotel_reservation.hotel.dto.response;

import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHotelDetailResponse {
    Long hotelId;

    String name;  //호텔 이름
    String address; //호텔 주소
    BigDecimal longtitude; //경도
    BigDecimal latitude; //위도
    String description; //호텔 설명
    Integer starLevel; //호텔 성급(ex.5성급)
    double rating; //별점
    Integer reviewCount; //리뷰 수
    String imageUrl; //메인이미지

    public static GetHotelDetailResponse toDto(HotelEntity hotel){
        return GetHotelDetailResponse.builder()
                .hotelId(hotel.getHotelId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .description(hotel.getDescription())
                .starLevel(hotel.getStarLevel())
                .rating(hotel.getRating())
                .build();
    }
}
