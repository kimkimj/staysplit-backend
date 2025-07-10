package staysplit.hotel_reservation.room.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateRoomRequest(

        @NotBlank(message = "객실 설명을 입력해 주세요.")
        String description,

        @NotBlank(message = "객실 타입을 입력해 주세요.")
        String roomType,

        @NotNull(message = "가격을 입력해 주세요.")
        @Positive(message = "가격은 0보다 커야 합니다.")
        Integer price,

        @NotNull(message = "수용 인원을 입력해 주세요.")
        @Min(value = 1, message = "수용 인원은 최소 1명 이상이어야 합니다.")
        Integer occupancy,

        @NotNull(message = "이 객실과 같은 타입의 객실 개수를 입력해주세요.")
        @Min(value = 1, message = "객실 수는 1개 이상 이어야 합니다.")
        Integer totalQuantity

) { }

