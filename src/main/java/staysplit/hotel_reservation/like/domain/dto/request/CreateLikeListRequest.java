package staysplit.hotel_reservation.like.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLikeListRequest {
    private Integer customerId;
    private String listName;
}
