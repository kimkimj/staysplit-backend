package staysplit.hotel_reservation.reservedRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.reservedRoom.entity.ReservedRoomEntity;


@Repository
public interface ReservedRoomRepository extends JpaRepository<ReservedRoomEntity, Integer>, CustomReservedRoomRepository {

}
