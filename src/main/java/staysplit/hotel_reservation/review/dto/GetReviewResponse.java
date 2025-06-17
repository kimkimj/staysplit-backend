package staysplit.hotel_reservation.review.dto;

public record GetReviewResponse(
        long id,
        long user_id,
        long hotel_id,
        String content,
        int rate
) {
}
