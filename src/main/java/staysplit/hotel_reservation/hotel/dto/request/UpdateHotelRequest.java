package staysplit.hotel_reservation.hotel.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHotelRequest {
    private Long hotelId;
    private Long providerId;
    private String name;
    private String address;
    private String description;
    private Integer starLevel;
    private Double rating;
    private String imageUrl;
}
