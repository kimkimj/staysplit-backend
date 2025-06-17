package staysplit.hotel_reservation.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {
    private ErrorCode errorCode;
    private String message;
}
