package staysplit.hotel_reservation.hotel.dto.response;

import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHotelListResponse {
    Long hotelId;

    String name;  //호텔 이름
    String address; //호텔 주소
    Integer starLevel; //호텔 성급(ex.5성급)
    double rating; //별점
    Integer reviewCount; //리뷰 수
    String imageUrl; //메인이미지


    public static GetHotelListResponse toDto(HotelEntity hotel){
        return GetHotelListResponse.builder()
                .hotelId(hotel.getHotelId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .starLevel(hotel.getStarLevel())
                .rating(hotel.getRating())
                .build();
    }
}
