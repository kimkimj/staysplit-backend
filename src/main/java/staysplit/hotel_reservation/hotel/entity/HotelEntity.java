package staysplit.hotel_reservation.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.hotel.dto.request.UpdateHotelRequest;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HotelEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long hotelId;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderEntity provider;


    private String name;  //호텔 이름
    private String address; //호텔 주소
    private BigDecimal longtitude; //경도
    private BigDecimal latitude; //위도
    private String description; //호텔 설명
    private Integer starLevel; //호텔 성급(ex.5성급)
    private Double rating; //별점
    private Integer reviewCount; //리뷰 수
    private String imageUrl; //메인 이미지

    public void updateHotel(UpdateHotelRequest request) {
        this.name = request.name();
        this.address = request.address();
        this.description = request.description();
        this.starLevel = request.starLevel();
        this.rating = request.rating();
        this.imageUrl = request.imageUrl();
    }


}
