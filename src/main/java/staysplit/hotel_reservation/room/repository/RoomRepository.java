package staysplit.hotel_reservation.room.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.room.domain.RoomEntity;


@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    Page<RoomEntity> findByHotel_HotelId(Long hotelId, Pageable pageable);

}
