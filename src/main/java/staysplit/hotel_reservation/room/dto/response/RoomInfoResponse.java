package staysplit.hotel_reservation.room.dto.response;

import staysplit.hotel_reservation.photo.service.PhotoUrlBuilder;
import staysplit.hotel_reservation.room.domain.RoomEntity;

import java.util.List;

public record RoomInfoResponse(
        Integer hotelId,
        String hotelName,
        Integer roomId,
        String roomType,
        String description,
        Integer maxOccupancy,
        Integer price,
        Integer totalQuantity,
        String mainImageUrl,
        List<String> additionalPhotoUrls
) {
    public static RoomInfoResponse from(RoomEntity room, PhotoUrlBuilder photoUrlBuilder) {
        String roomMainUrl = room.getMainPhoto().buildFullUrl(photoUrlBuilder);
        List<String> roomAdditionalUrls = room.getAdditionalPhotos().stream()
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
                roomMainUrl,
                roomAdditionalUrls);
    }
}