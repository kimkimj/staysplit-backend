package staysplit.hotel_reservation.room.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateRoomRequest(

        @NotNull(message = "Room ID를 입력해 주세요.")
        Integer roomId,

        @NotBlank(message = "객실에 대한 설명을 입력해 주세요.")
        String description,

        @NotBlank(message = "객실 타입을 입력해 주세요.")
        String roomType,

        @Positive(message = "가격은 0이상이어야 합니다.")
        Integer price,

        @Min(value = 1, message = "최소 수용 인원은 1이상 이어야 합니다.")
        Integer maxOccupancy,

        @NotNull(message = "이 객실과 같은 타입의 객실 개수를 입력해주세요.")
        @Min(value = 1, message = "객실 수는 1개 이상 이어야 합니다.")
        Integer totalQuantity
) {
}
