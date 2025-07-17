package staysplit.hotel_reservation.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import staysplit.hotel_reservation.like.domain.entity.LikeListEntity;

import java.util.List;

public interface LikeListRepository  extends JpaRepository<LikeListEntity, Integer>
{
    List<LikeListEntity> findByCustomerId(Integer customerId);
}
