package staysplit.hotel_reservation.photo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;
import staysplit.hotel_reservation.photo.domain.entity.PhotoEntity;
import staysplit.hotel_reservation.photo.repository.PhotoRepository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.repository.RoomRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final ProviderRepository providerRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public void changeMainPhoto(Integer photoId, String associatedEntity, String email) {
        ProviderEntity provider = validateProvider(email);
        PhotoEntity photo = validatePhotoById(photoId);
        photo.makeMainPhoto();

        if (associatedEntity.equals("hotel")) {
            HotelEntity hotel = photo.getHotel();
            hotel.changeMainPhoto(photo);
        } else if (associatedEntity.equals("room")){
            RoomEntity room = photo.getRoom();
            room.changeMainPhoto(photo);
        } else {
            throw new AppException(ErrorCode.INVALID_PHOTO_TYPE, ErrorCode.INVALID_PHOTO_TYPE.getMessage());
        }
    }

    private PhotoEntity validatePhotoById(Integer photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new AppException(ErrorCode.PHOTO_NOT_FOUND, ErrorCode.PHOTO_NOT_FOUND.getMessage()));
    }

    private void hasAuthorityToHotel(ProviderEntity provider, HotelEntity hotel) {
        if (!hotel.getProvider().getId().equals(provider.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_PROVIDER, ErrorCode.UNAUTHORIZED_PROVIDER.getMessage());
        }
    }

    private void hasAuthorityToRoom(ProviderEntity provider, RoomEntity room) {
        if (!room.getHotel().getProvider().getId().equals(provider.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_PROVIDER, ErrorCode.UNAUTHORIZED_PROVIDER.getMessage());
        }
    }

    private ProviderEntity validateProvider(String email) {
        return providerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private HotelEntity validateHotelById(Long hotelId) {
        return hotelRepository.findByHotelId(hotelId)
                .orElseThrow(() -> new AppException(ErrorCode.HOTEL_NOT_FOUND, ErrorCode.HOTEL_NOT_FOUND.getMessage()));
    }

    private RoomEntity validateRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, ErrorCode.ROOM_NOT_FOUND.getMessage()));
    }
}
