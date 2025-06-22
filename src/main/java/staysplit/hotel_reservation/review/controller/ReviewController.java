package staysplit.hotel_reservation.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.review.domain.dto.request.CreateReviewRequest;
import staysplit.hotel_reservation.review.domain.dto.request.ModifyReviewRequest;
import staysplit.hotel_reservation.review.domain.dto.response.CreateReviewResponse;
import staysplit.hotel_reservation.review.domain.dto.response.GetReviewResponse;
import staysplit.hotel_reservation.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @PostMapping
    public Response<CreateReviewResponse> createReview(@RequestBody CreateReviewRequest request){
        CreateReviewResponse response = reviewService.createReview(request);
        return Response.success(response);
    }

    @GetMapping("/users/{userId}")
    public Page<GetReviewResponse> getReviewByUserId(@PathVariable Long userId, @PageableDefault(size = 10, sort = "reviewId", direction = Sort.Direction.ASC) Pageable pageable) {
        return reviewService.getReviewByUserId(userId, pageable);
    }

    @GetMapping("/hotels/{hotelId}")
    public Page<GetReviewResponse> getReviewByHotelId(@PathVariable Long hotelId, @PageableDefault(size = 10, sort = "reviewId", direction = Sort.Direction.ASC) Pageable pageable) {
        return reviewService.getReviewByHotelId(hotelId, pageable);
    }

    @DeleteMapping("/{reviewId}")
    public Response<String> deleteReview(@RequestParam Long userId, @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return Response.success("리뷰가 삭제되었습니다.");
    }

    @PutMapping("/{reviewId}")
    public Response<String> modifyReview( @PathVariable Long reviewId, @RequestBody ModifyReviewRequest request) {
        reviewService.modifyReview(reviewId, request);
        return Response.success("리뷰가 수정되었습니다.");
    }
}
