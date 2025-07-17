package staysplit.hotel_reservation.like.domain.dto.response;

import staysplit.hotel_reservation.like.domain.entity.LikeListEntity;

public record GetLikeShareResponse(
        Integer likeListCustomerId,
        Integer customerId,
        LikeListEntity likeList
) {
}
