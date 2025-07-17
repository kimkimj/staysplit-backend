package staysplit.hotel_reservation.like.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import staysplit.hotel_reservation.like.domain.dto.response.GetLikeHotelResponse;
import staysplit.hotel_reservation.like.domain.entity.LikeListHotelEntity;


public interface LikeListHotelRepository  extends JpaRepository<LikeListHotelEntity, Integer> {
    Page<GetLikeHotelResponse> findByLikeListId(Integer likeListId, Pageable pageable);
    void deleteByLikeListIdAndHotelId(Integer likeListId, Integer hotelId);
    void deleteByLikeListId(Integer likeListId);
}
