package staysplit.hotel_reservation.reservedRoom.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CustomReservedRoomRepository {
    // query dsl을 사용할 method
    int countReservedRoomsForDateRange(Integer roomId, LocalDate checkInDate, LocalDate checkOutDate);
}

