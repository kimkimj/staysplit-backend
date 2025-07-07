package staysplit.hotel_reservation.reservation.reposiotry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.reservation.domain.entity.ReservationParticipantEntity;

@Repository
public interface ReservationParticipantRepository extends JpaRepository<ReservationParticipantEntity, Integer> {
}
