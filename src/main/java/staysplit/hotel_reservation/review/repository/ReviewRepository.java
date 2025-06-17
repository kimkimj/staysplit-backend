package staysplit.hotel_reservation.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.review.domain.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewByHotel_id(long hotel_id);
    List<Review> getReviewByUser_id(long user_id, long hotel_id);
    boolean editReviewByHotel_id(long user_id, long review_id, long hotel_id);
}
