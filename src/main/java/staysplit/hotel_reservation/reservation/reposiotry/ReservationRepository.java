package staysplit.hotel_reservation.reservation.reposiotry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.reservation.domain.entity.ReservationEntity;
import staysplit.hotel_reservation.reservation.reposiotry.search.ReservationRepositoryCustom;


@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer>, ReservationRepositoryCustom {

}
