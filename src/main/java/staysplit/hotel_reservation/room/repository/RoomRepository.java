package staysplit.hotel_reservation.room.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.room.domain.RoomEntity;


@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    Page<RoomEntity> findByHotel_Id(Integer hotelId, Pageable pageable);
}
