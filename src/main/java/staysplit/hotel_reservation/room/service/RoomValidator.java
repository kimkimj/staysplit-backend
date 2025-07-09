package staysplit.hotel_reservation.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.repository.RoomRepository;

@Component
@RequiredArgsConstructor
public class RoomValidator {
    private final RoomRepository roomRepository;

    public RoomEntity validateRoom(Integer roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, ErrorCode.ROOM_NOT_FOUND.getMessage()));
    }
}
