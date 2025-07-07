package staysplit.hotel_reservation.reservedRoom.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.reservation.domain.entity.QReservationEntity;
import staysplit.hotel_reservation.reservation.domain.enums.ReservationStatus;
import staysplit.hotel_reservation.reservedRoom.entity.QReservedRoomEntity;

import java.time.LocalDate;

@Repository
public class ReservedRoomRepositoryImpl implements CustomReservedRoomRepository {
    private final JPAQueryFactory queryFactory;

    public ReservedRoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 겹치는 방 수를 계산
    @Override
    public int countReservedRoomsForDateRange(Integer roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        QReservedRoomEntity reservedRoom = QReservedRoomEntity.reservedRoomEntity;

        Integer count = queryFactory
                .select(reservedRoom.quantity.sum()) // 예약된 방의 총 수량 합
                .from(reservedRoom)
                .join(reservedRoom.reservation, reservation)
                .where(
                        reservedRoom.room.id.eq(roomId),
                        reservation.status.eq(ReservationStatus.CONFIRMED),
                        reservation.checkOutDate.gt(checkInDate),
                        reservation.checkInDate.lt(checkOutDate)
                )
                .fetchOne();

        return count != null ? count : 0;
    }
}
