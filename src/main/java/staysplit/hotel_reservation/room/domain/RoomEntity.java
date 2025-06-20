package staysplit.hotel_reservation.room.domain;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    HotelEntity hotel;

    @Column(nullable = false)
    String roomType;

    String photoUrl;

    @Column(nullable = false)
    Integer maxOccupancy;

    @Column(nullable = false)
    Integer price;

    String description;

    public void changeRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void changePhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;

    }
}
