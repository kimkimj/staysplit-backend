package staysplit.hotel_reservation.room.mapper;

import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.photo.domain.PhotoEntity;
import staysplit.hotel_reservation.photo.service.PhotoUrlBuilder;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.dto.response.RoomInfoResponse;

import java.util.List;
import java.util.Optional;

@Component
public class RoomMapper {
    public RoomInfoResponse toRoomInfoResponse(RoomEntity room, PhotoUrlBuilder photoUrlBuilder) {

        Optional<PhotoEntity> mainPhoto = room.getMainPhoto();

        String mainUrl = mainPhoto.isPresent() ? mainPhoto.get().buildFullUrl(photoUrlBuilder) : null;

                List < String > additionalUrls = room.getPhotos()
                .stream()
                .filter(photo -> !photo.isMainPhoto())
                .map(photo -> photo.buildFullUrl(photoUrlBuilder))
                .toList();

        return new RoomInfoResponse(
                room.getHotel().getHotelId(),
                room.getHotel().getName(),
                room.getId(),
                room.getRoomType(),
                room.getDescription(),
                room.getMaxOccupancy(),
                room.getPrice(),
                room.getTotalQuantity(),
                mainUrl,
                additionalUrls);
    }
}
