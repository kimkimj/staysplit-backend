package staysplit.hotel_reservation.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.hotel.dto.request.UpdateHotelRequest;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HotelEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Integer hotelId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private ProviderEntity provider;

    private String name;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String description;

    private Integer starLevel;

    @Builder.Default
    private Double rating = 0.0;

    @Builder.Default
    private Integer reviewCount = 0;

    public void updateHotel(UpdateHotelRequest request) {
        this.name = request.name();
        this.address = request.address();
        this.longitude = request.longitude();
        this.latitude = request.latitude();
        this.description = request.description();
        this.starLevel = request.starLevel();
        this.rating = request.rating();
    }
}