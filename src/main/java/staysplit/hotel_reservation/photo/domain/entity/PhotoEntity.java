package staysplit.hotel_reservation.photo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import staysplit.hotel_reservation.photo.domain.enums.DisplayOrder;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.room.domain.RoomEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    @Column(nullable = false)
    private String uploadFileName;

    @Column(nullable = false)
    private String storedFileName;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DisplayOrder displayOrder = DisplayOrder.PHOTOS;

    @CreatedDate
    private LocalDateTime createdAt;

    public void makeMainPhoto() {
        this.displayOrder = DisplayOrder.MAIN;
    }

    public void makeRegularPhoto() {
        this.displayOrder = DisplayOrder.PHOTOS;
    }

    public boolean isMain() {
        return this.displayOrder == DisplayOrder.MAIN;
    }
}

