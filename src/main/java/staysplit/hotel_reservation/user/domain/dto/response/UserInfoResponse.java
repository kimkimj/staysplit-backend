package staysplit.hotel_reservation.user.domain.dto.response;

import staysplit.hotel_reservation.user.domain.entity.UserEntity;

public record UserInfoResponse(
        String role,
        String email
) {
    public static UserInfoResponse from(UserEntity userEntity) {
        return new UserInfoResponse(userEntity.getRole().toString(), userEntity.getEmail());
    }
}

