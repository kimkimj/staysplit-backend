package staysplit.hotel_reservation.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T result;
    private T data;

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result,null);
    }

    public static <T> Response<T> error(T result) {
        return new Response<>("ERROR", result,null);
    }
    public static <T> Response<T> warn(T result, T data) {
        return new Response<>("WARN", result,data);
    }

}
