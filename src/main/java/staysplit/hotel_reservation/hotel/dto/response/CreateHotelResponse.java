package staysplit.hotel_reservation.hotel.dto.response;


import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHotelResponse {

    private Long hotelId;
    private String name;
    private String address;
    private String description;
    private Integer starLevel;
    private double rating;
    private String imageUrl;

    public static CreateHotelResponse toDto(HotelEntity hotel){
        return CreateHotelResponse.builder()
                .hotelId(hotel.getHotelId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .description(hotel.getDescription())
                .starLevel(hotel.getStarLevel())
                .rating(hotel.getRating())
                .build();
    }
}
