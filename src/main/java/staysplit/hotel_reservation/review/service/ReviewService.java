package staysplit.hotel_reservation.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.review.domain.dto.request.CreateReviewRequest;
import staysplit.hotel_reservation.review.domain.dto.response.CreateReviewResponse;
import staysplit.hotel_reservation.review.domain.dto.response.DeleteReviewResponse;
import staysplit.hotel_reservation.review.domain.dto.response.GetReviewResponse;
import staysplit.hotel_reservation.review.domain.entity.ReviewEntity;
import staysplit.hotel_reservation.review.repository.ReviewRepository;
import staysplit.hotel_reservation.common.exception.ErrorCode;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public CreateReviewResponse createReview(CreateReviewRequest request){
        ReviewEntity review = ReviewEntity.builder()
                .reviewId(request.id())
                .hotelId(request.hotelId())
                .userId(request.userId())
                .content(request.content())
                .rating(request.rating())
                .build();

        reviewRepository.save(review);
        return CreateReviewResponse.from(review);
    }

    @Transactional(readOnly = true)
    public Page<GetReviewResponse> getReviewByUserId(Long userId, Pageable pageable){
        Page<ReviewEntity> review = reviewRepository.getReviewByUserId(userId, pageable);
        return review.map(GetReviewResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<GetReviewResponse> getReviewByHotelId(Long  hotelId, Pageable pageable){
        Page<ReviewEntity> review = reviewRepository.getReviewByHotelId(hotelId, pageable);
        return review.map(GetReviewResponse::from);
    }

    public GetReviewResponse modifyReview(ReviewEntity targetReview){
        ReviewEntity review = validateReview(targetReview.getReviewId());
        hasAuthority(review, targetReview.getUserId(), targetReview.getReviewId());

        review.setContent(targetReview.getContent());
        review.setRating(targetReview.getRating());

        return GetReviewResponse.from(review);
    }

    public DeleteReviewResponse deleteReview(Long userId, Long reviewId) {
        ReviewEntity review = validateReview(reviewId);
        hasAuthority(review, userId, reviewId);
        DeleteReviewResponse response = new DeleteReviewResponse(userId, reviewId);
        reviewRepository.deleteReview(userId, reviewId);
        return response;
    }

    private ReviewEntity validateReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND,
                        ErrorCode.REVIEW_NOT_FOUND.getMessage()));
    }

    private void hasAuthority(ReviewEntity review, Long userId, Long reviewId) {
        if(!(review.getReviewId().equals(reviewId) && review.getUserId().equals(userId))){
            throw new AppException(ErrorCode.UNAUTHORIZED_REVIEWER,
                    ErrorCode.UNAUTHORIZED_REVIEWER.getMessage());
        }
    }
}
