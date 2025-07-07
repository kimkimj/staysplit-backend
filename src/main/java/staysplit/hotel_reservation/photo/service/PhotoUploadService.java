package staysplit.hotel_reservation.photo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.service.HotelValidator;
import staysplit.hotel_reservation.photo.domain.DisplayType;
import staysplit.hotel_reservation.photo.domain.PhotoEntity;
import staysplit.hotel_reservation.photo.dto.response.PhotoDetailResponse;
import staysplit.hotel_reservation.photo.repository.PhotoRepository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.service.ProviderValidator;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.service.RoomValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoUploadService {

    @Value("${file.dir}")
    private String fileDir;

    private final PhotoRepository photoRepository;
    private final ProviderValidator providerValidator;
    private final HotelValidator hotelValidator;
    private final RoomValidator roomValidator;

    public List<PhotoDetailResponse> uploadPhotos(String email, String entityType, Integer entityId, List<String> displayType,
                                                  List<MultipartFile> multipartFiles) throws IOException {
        List<PhotoDetailResponse> storedFileList = new ArrayList<>();
        for (int i = 0; i < displayType.size(); i++) {
            PhotoDetailResponse photoDetailResponse = uploadPhoto(email, entityType, entityId, displayType.get(i), multipartFiles.get(i));
            storedFileList.add(photoDetailResponse);
        }
        return storedFileList;
    }

    public PhotoDetailResponse uploadPhoto(String email, String entityType, Integer entityId, String displayType,
                                           MultipartFile multipartFile) throws IOException {
        ProviderEntity provider = providerValidator.validateProvider(email);

        HotelEntity hotel = null;
        RoomEntity room = null;
        if (entityType.equals("HOTEL")) {
            hotel = hotelValidator.validateHotel(entityId);
        } else if (entityType.equals("ROOM")) {
            room = roomValidator.validateRoom(entityId);
        } else {
            throw new AppException(ErrorCode.INVALID_ENTITY_TYPE, ErrorCode.INVALID_ENTITY_TYPE.getMessage());
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storedFileName = createStoredFileName(originalFileName);

        multipartFile.transferTo(new File(getFullPath(storedFileName)));

        PhotoEntity photo = PhotoEntity.builder()
                .uploadFileName(originalFileName)
                .storedFileName(storedFileName)
                .displayType(getDisplayType(displayType))
                .hotel(hotel)
                .room(room)
                .build();

        photoRepository.save(photo);

        return PhotoDetailResponse.from(photo);
    }

    public String getFullPath(String filename) {

        //return fileDir + filename;
        return Paths.get(fileDir, filename).toString();
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

    private DisplayType getDisplayType(String displayType) {
        if (displayType.equals("MAIN")) {
            return DisplayType.MAIN;
        }
        return DisplayType.ADDITIONAL_PHOTO;
    }

}