package staysplit.hotel_reservation.review.dto;

import jakarta.persistence.Column;

public record CreateReviewRequest(
   long id,
   long user_id,
   long hotel_id,
   String content,
   int rate
) {}
