package staysplit.hotel_reservation.review.domain.dto.response;

public record DeleteReviewResponse(
        Long userId,
        Long reviewId
) {
}
