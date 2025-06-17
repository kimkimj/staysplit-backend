package staysplit.hotel_reservation.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.review.domain.Review;
import staysplit.hotel_reservation.review.dto.CreateReviewRequest;
import staysplit.hotel_reservation.review.dto.GetReviewResponse;
import staysplit.hotel_reservation.review.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review createReview(CreateReviewRequest createReviewRequest){
    //FIXME: 리뷰의 중복체크?
        Review review = Review.builder()
                .id(createReviewRequest.id())
                .hotel_id(createReviewRequest.hotel_id())
                .user_id(createReviewRequest.user_id())
                .content(createReviewRequest.content())
                .rate(createReviewRequest.rate())
                .build();

        reviewRepository.save(review);
        return review;
    }

    //FIXME:리뷰는 내 계정의 리뷰페이지에서보는건지, 호텔페이지에서 보는건지
    public List<Review> getReviewByUser_id(long user_id, long  hotel_id){
        return reviewRepository.getReviewByUser_id(user_id, hotel_id);
    }

    public List<Review> getReviewByHotel_id(long  hotel_id){
        return reviewRepository.getReviewByHotel_id(hotel_id);
    }

    public boolean editReview(long user_id,long review_id, long  hotel_id){
        return reviewRepository.editReviewByHotel_id(user_id,review_id, hotel_id);
    }

}
