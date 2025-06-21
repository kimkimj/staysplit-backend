package staysplit.hotel_reservation.review.domain.dto.request;

public record CreateReviewRequest(
   Long id,
   Long userId,
   Long hotelId,
   String content,
   Integer rating
) {}
