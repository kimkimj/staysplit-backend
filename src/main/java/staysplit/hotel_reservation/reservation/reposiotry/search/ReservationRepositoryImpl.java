package staysplit.hotel_reservation.reservation.reposiotry.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import staysplit.hotel_reservation.reservation.domain.entity.QReservationEntity;
import staysplit.hotel_reservation.reservation.domain.entity.QReservationParticipantEntity;
import staysplit.hotel_reservation.reservation.domain.entity.ReservationEntity;
import staysplit.hotel_reservation.reservation.domain.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // customer id, Reservation Status, 특정 Local Date 이후로 검색 가능
    @Override
    public Page<ReservationEntity> findAllReservationByCustomerWithFilters(Integer customerId,
                                                                          ReservationStatus status,
                                                                          LocalDate afterDate,
                                                                          Pageable pageable) {
        QReservationEntity reservation = QReservationEntity.reservationEntity;
        QReservationParticipantEntity participant = QReservationParticipantEntity.reservationParticipantEntity;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(participant.customer.id.eq(customerId));

        if (status != null) {
            booleanBuilder.and(reservation.status.eq(status));
        }

        if (afterDate != null) {
            booleanBuilder.and(reservation.checkInDate.gt(afterDate));
        }

        List<ReservationEntity> results = queryFactory
                .selectFrom(reservation)
                .distinct()
                .join(reservation.participants, participant)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(reservation.count())
                .from(reservation)
                .join(reservation.participants, participant)
                .where(booleanBuilder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }
}
