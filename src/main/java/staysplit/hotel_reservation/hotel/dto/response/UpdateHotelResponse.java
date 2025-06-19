package staysplit.hotel_reservation.hotel.dto.response;


import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHotelResponse {
    private String name;
    private String address;
    private String description;
    private Integer starLevel;
    private double rating;
    private String imageUrl;

    public static UpdateHotelResponse toDto(HotelEntity hotel){
        return UpdateHotelResponse.builder()
                //.hotelId(hotel.getHotelId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .description(hotel.getDescription())
                .starLevel(hotel.getStarLevel())
                .rating(hotel.getRating())
                .imageUrl(hotel.getImageUrl())
                .build();
    }
}
