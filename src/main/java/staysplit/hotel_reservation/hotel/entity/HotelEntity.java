package staysplit.hotel_reservation.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.hotel.dto.request.UpdateHotelRequest;
import staysplit.hotel_reservation.photo.domain.entity.PhotoEntity;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @Column(nullable = false)
    private String name;  //호텔 이름
    @Column(nullable = false)
    private String address; //호텔 주소
    private BigDecimal longitude; //경도
    private BigDecimal latitude; //위도
    private String description; //호텔 설명
    private Integer starLevel; //호텔 성급(ex.5성급)

    @Builder.Default
    private Double rating = 0.0; //별점

    @Builder.Default
    private Integer reviewCount = 0; //리뷰 수

    @Builder.Default
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoEntity> hotelPhotos = new ArrayList<>();

    @OneToOne
    private PhotoEntity mainPhoto;

    public void updateHotel(UpdateHotelRequest request) {
        this.name = request.name();
        this.address = request.address();
        this.longitude = request.longitude();
        this.latitude = request.latitude();
        this.description = request.description();
        this.starLevel = request.starLevel();
        this.rating = request.rating();
    }

    public void changeMainPhoto(PhotoEntity mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

}
