package staysplit.hotel_reservation.like.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import staysplit.hotel_reservation.like.domain.dto.response.GetLikeShareResponse;
import staysplit.hotel_reservation.like.domain.entity.LikeListCustomerEntity;

import java.util.List;

public interface LikeListCustomerRepository  extends JpaRepository<LikeListCustomerEntity, Integer> {
    Page<GetLikeShareResponse> findByCustomerId(Integer customerId, Pageable pageable);
    void deleteByLikeListId(Integer likeListId);
}
