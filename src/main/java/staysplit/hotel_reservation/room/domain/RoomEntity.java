package staysplit.hotel_reservation.room.domain;

import jakarta.persistence.*;
import lombok.*;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.domain.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @Column(nullable = false)
    private String roomType;

    private Integer quantity;

    private String photoUrl;

    @Column(nullable = false)
    private Integer maxOccupancy;

    @Column(nullable = false)
    private Integer price;

    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoEntity> roomPhotos = new ArrayList<>();

    @OneToOne
    private PhotoEntity mainPhoto;

    public void updateRoom(String roomType, Integer maxOccupancy,
                           Integer price, String description, Integer quantity) {
        this.roomType = roomType;
        this.quantity = quantity;
        this.maxOccupancy = maxOccupancy;
        this.price = price;
        this.description = description;
    }

    public void changeMainPhoto(PhotoEntity mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

}
