package staysplit.hotel_reservation.user.domain.dto.response;

import staysplit.hotel_reservation.user.domain.entity.UserEntity;

public record UserDetailResponse(
        String role,
        String email
) {
    public static UserDetailResponse from(UserEntity userEntity) {
        return new UserDetailResponse(userEntity.getRole().toString(), userEntity.getEmail());
    }
}

