package staysplit.hotel_reservation.review.domain.dto.request;

public record ModifyReviewRequest (
        Integer customerId,
        String content,
        Integer rating
){

}
