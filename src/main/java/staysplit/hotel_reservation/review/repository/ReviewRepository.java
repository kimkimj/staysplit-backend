package staysplit.hotel_reservation.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.review.domain.entity.ReviewEntity;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    @Query("SELECT r FROM ReviewEntity r WHERE r.hotel.id = :hotelId AND r.deletedAt IS NULL ORDER BY r.createdAt, r.id DESC")
    Page<ReviewEntity> getReviewByHotelId(@Param("hotelId") Integer hotelId, Pageable pageable);

    @Query("SELECT r FROM ReviewEntity r WHERE r.customer.id = :customerId AND r.deletedAt IS NULL ORDER BY r.createdAt, r.id DESC")
    Page<ReviewEntity> getReviewByCustomerId(@Param("customerId") Integer customerId, Pageable pageable);

    @Query("SELECT COUNT(r) > 0 FROM ReviewEntity r WHERE r.customer.id = :customerId AND r.hotel.id = :hotelId AND r.deletedAt IS NULL")
    boolean existsByUserIdAndHotelId(@Param("customerId") Integer customerId, @Param("hotelId") Integer hotelId);
}
