package staysplit.hotel_reservation.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import staysplit.hotel_reservation.review.domain.Review;
import staysplit.hotel_reservation.review.dto.GetReviewResponse;
import staysplit.hotel_reservation.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @PostMapping("/create-review")
    public Review createReview(){
        return null;
    }

    @GetMapping()
    public List<Review> getReviewByUser_id(long user_id, long hotel_id) {
        return reviewService.getReviewByUser_id(user_id, hotel_id);
    }

    @GetMapping()
    public List<Review> getReviewByHotel_id( long hotel_id) {
        return reviewService.getReviewByHotel_id(hotel_id);
    }

}
