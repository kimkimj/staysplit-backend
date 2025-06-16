package staysplit.hotel_reservation.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {
    private ErrorCode errorCode;
    private String message;
}
