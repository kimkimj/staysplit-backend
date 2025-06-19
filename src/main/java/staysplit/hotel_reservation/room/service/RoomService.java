package staysplit.hotel_reservation.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.domain.dto.request.RoomCreateRequest;
import staysplit.hotel_reservation.room.domain.dto.request.RoomModifyRequest;
import staysplit.hotel_reservation.room.domain.dto.response.RoomInfoResponse;
import staysplit.hotel_reservation.room.repository.RoomRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ProviderRepository providerRepository;
    private final HotelRepository hotelRepository;

    public RoomInfoResponse createRoom(String email, RoomCreateRequest request) {
        ProviderEntity provider = validateProvider(email);

        RoomEntity room = RoomEntity.builder()
                .hotel(provider.getHotel())
                .photoUrl(request.photoUrl())
                .roomType(request.roomType())
                .price(request.price())
                .description(request.description())
                .maxOccupancy(request.occupancy())
                .roomType(request.roomType()).roomType()
                .build();

        roomRepository.save(room);
        return RoomInfoResponse.from(room);
    }

    public RoomInfoResponse modifyRoom(String email, RoomModifyRequest request) {
        RoomEntity room = validateRoom(request.roomId());
        ProviderEntity provider = validateProvider(email);
        if (hasAuthority(provider, room)) {
            room.changeRoomType(request.roomType());
            room.changeDescription(request.description());
            room.changePrice(request.price());
            room.changePhotoUrl(request.photoUrl());
            room.changeMaxOccupancy(request.maxOccupancy());
        }
        return RoomInfoResponse.from(room);
    }

    public String deleteRoom(String email, Long roomId) {
        RoomEntity room = validateRoom(roomId);
        ProviderEntity provider = validateProvider(email);
        if (provider.getId() != room.getHotel().getProvider().getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED_PROVIDER,
                    ErrorCode.UNAUTHORIZED_PROVIDER.getMessage());
        }
        roomRepository.delete(room);

        return "방이 성공적으로 삭제되었습니다";
    }

    public RoomInfoResponse findRoomById(Long roomId) {
        RoomEntity room = validateRoom(roomId);
        return RoomInfoResponse.from(room);
    }

    public Page<RoomInfoResponse> findAllRoomsByHotel(Long hotelId, Pageable pageable) {
        HotelEntity hotel = validateHotelById(hotelId);
        Page<RoomEntity> hotels = roomRepository.findByHotel(hotelId, pageable);
        return hotels.map(RoomInfoResponse::from);
    }


    // ------------//

    private boolean hasAuthority(ProviderEntity provider, RoomEntity room) {

        if (room.getHotel().getProvider().getId() != provider.getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED_PROVIDER,
                    ErrorCode.UNAUTHORIZED_PROVIDER.getMessage());
        }
        return true;
    }

    private HotelEntity validateHotelById(Long hotelId) {
        return hotelRepository.findByHotelId(hotelId)
                .orElseThrow(() -> new AppException(ErrorCode.HOTEL_NOT_FOUND,
                        ErrorCode.HOTEL_NOT_FOUND.getMessage()));
    }

    private RoomEntity validateRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND,
                        ErrorCode.ROOM_NOT_FOUND.getMessage()));
    }

    private ProviderEntity validateProvider(String email) {
        return providerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                        ErrorCode.USER_NOT_FOUND.getMessage()));
    }
}
