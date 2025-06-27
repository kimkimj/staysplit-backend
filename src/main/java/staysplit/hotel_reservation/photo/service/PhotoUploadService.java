package staysplit.hotel_reservation.photo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoUploadService {

    @Value("${file.dir}")
    private String fileDir;

    private final PhotoRepository photoRepository;
    private final ProviderRepository providerRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public void saveFile(MultipartFile multipartFile, String entityType,
                         Long entityId, String email) throws IOException {

        ProviderEntity provider = validateProvider(email);

        String originalFilename = multipartFile.getOriginalFilename();
        String storedFileName = createStoredFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storedFileName)));

        PhotoEntity photo;
        if (entityType.equals("hotel")) {

            HotelEntity hotel = validateHotelById(entityId);
            hasAuthorityToHotel(provider, hotel);

            String key = "hotels/" + storedFileName;

            photo = PhotoEntity.builder()
                    .path("hotels")
                    .uploadFileName(originalFilename)
                    .storedFileName(storedFileName)
                    .hotel(hotel)
                    .build();

        } else {
            RoomEntity room = validateRoomById(entityId);
            hasAuthorityToRoom(provider, room);

            String key = "rooms/"  + storedFileName;

            photo = PhotoEntity.builder()
                    .path("rooms")
                    .uploadFileName(originalFilename)
                    .storedFileName(storedFileName)
                    .room(room)
                    .build();
        }
        photoRepository.save(photo);
    }

    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String createStoredFileName(String originalFileName) {
        String extension = extractExtension(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private String extractExtension(String originalFileName) {
        int index = originalFileName.lastIndexOf(".");
        return originalFileName.substring(index + 1);
    }

    private void hasAuthorityToHotel(ProviderEntity provider, HotelEntity hotel) {
        if (!hotel.getProvider().getId().equals(provider.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_PROVIDER, ErrorCode.UNAUTHORIZED_PROVIDER.getMessage());
        }
    }

    private void hasAuthorityToRoom(ProviderEntity provider, RoomEntity room) {
        if (room.getHotel().getProvider().getId().equals(provider.getId())) {
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
