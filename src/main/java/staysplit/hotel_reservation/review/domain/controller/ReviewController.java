package staysplit.hotel_reservation.review.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.review.domain.dto.response.GetReviewResponse;
import staysplit.hotel_reservation.review.domain.entity.ReviewEntity;
import staysplit.hotel_reservation.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @PostMapping("/create-review")
    public ReviewEntity createReview(){
        return null;
    }

    @GetMapping("/{userId}")
    public Page<GetReviewResponse> getReviewByUserId(Long userId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return reviewService.getReviewByUserId(userId, pageable);
    }

    @GetMapping("/{hotelId}")
    public Page<GetReviewResponse> getReviewByHotelId(Long hotelId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return reviewService.getReviewByHotelId(hotelId, pageable);
    }

    @DeleteMapping("/my")
    public Response<String> deleteReview(Long userId, Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return Response.success("리뷰가 삭제되었습니다.");
    }

    @PutMapping()
    public Response<String> modifyReview(ReviewEntity targetReview) {
        reviewService.modifyReview(targetReview);
        return Response.success("리뷰가 수정되었습니다.");
    }
}
