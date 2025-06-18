package staysplit.hotel_reservation.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private long hotelId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, foreignKey = @ForeignKey(name = "fk_hotels_provider"))
    private ProviderEntity provider;


    private String name;  //호텔 이름
    private String address; //호텔 주소
    private BigDecimal longtitude; //경도
    private BigDecimal latitude; //위도
    private String description; //호텔 설명
    private double rating; //별점
    private Integer review_count; //리뷰 수
    private String image_url; //메인이미지

}
