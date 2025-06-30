package staysplit.hotel_reservation.review.domain.dto.request;

public record CreateReviewRequest(
   Integer id,
   Integer customerId,
   String nickname,
   Integer hotelId,
   String content,
   Integer rating
) {}
