package staysplit.hotel_reservation.like.domain.dto.response;

public record GetLikeHotelResponse(
        Integer id,
        Integer customerId,
        Integer hotelId
) {
}
