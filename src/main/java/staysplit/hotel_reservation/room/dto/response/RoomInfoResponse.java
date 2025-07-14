package staysplit.hotel_reservation.room.dto.response;

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

}