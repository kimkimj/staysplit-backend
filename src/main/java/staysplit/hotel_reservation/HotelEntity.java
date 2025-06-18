package staysplit.hotel_reservation;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false, foreignKey = @ForeignKey(name = "fk_hotels_provider"))
    private ProviderEntity provider;

    @Column(nullable = false)
    private String name;  // 호텔 이름

    @Column(nullable = false)
    private String address; // 호텔 주소

    @Column(precision = 10, scale = 6)
    private BigDecimal longitude; // 경도

    @Column(precision = 10, scale = 6)
    private BigDecimal latitude; // 위도

    @Column(length = 1000)
    private String description; // 호텔 설명

    @Column(nullable = false)
    private double rating; // 별점

    @Column(name = "review_count")
    private Integer reviewCount; // 리뷰 수

    @Column(name = "image_url")
    private String imageUrl; // 메인이미지

    private Integer stars;
}
