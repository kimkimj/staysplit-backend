package staysplit.hotel_reservation.provider.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.user.domain.dto.entity.UserEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProviderEntity {

    @Id
    private Long id;

    @MapsId // 이렇게 하면 user_id가 이 클래스의 PK
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    public void addHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }
}
