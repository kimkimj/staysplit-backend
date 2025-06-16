package staysplit.hotel_reservation.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // USER
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "중복되는 닉네임입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "사용자의 이메일이 중복됩니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.CONFLICT, "비밀번호가 틀립니다.");

    private HttpStatus httpStatus;
    private String message;
}
