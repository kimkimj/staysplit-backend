package staysplit.hotel_reservation.reservation.reposiotry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.reservation.domain.entity.ReservationParticipantEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationParticipantRepository extends JpaRepository<ReservationParticipantEntity, Integer> {

    @Query("SELECT rp " +
            "FROM ReservationParticipantEntity rp " +
            "JOIN FETCH rp.reservation r " +
            "WHERE r.id = :reservationId")
    List<ReservationParticipantEntity> findByReservationId(@Param("reservationId") Integer reservationId);


    @Query("SELECT rp " +
            "FROM ReservationParticipantEntity rp " +
            "JOIN FETCH rp.reservation r " +
            "JOIN FETCH rp.customer c " +
            "WHERE r.id = :reservationId AND c.id = :customerId")
    Optional<ReservationParticipantEntity> findByCustomerIdAndReservationId(@Param("customerId") Integer customerId,
                                                                            @Param("reservationId") Integer reservationId);
}
