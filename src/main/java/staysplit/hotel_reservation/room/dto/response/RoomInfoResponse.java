package staysplit.hotel_reservation.room.dto.response;

import staysplit.hotel_reservation.room.domain.RoomEntity;

public record RoomInfoResponse(
        Integer hotelId,
        String hotelName,
        Integer roomId,
        String roomType,
        Integer maxOccupancy,
        Integer price,
        Integer totalQuantity
) {
    public static RoomInfoResponse from(RoomEntity room) {
        return new RoomInfoResponse(room.getHotel().getHotelId(),
                room.getHotel().getName(),
                room.getId(),
                room.getRoomType(),
                room.getMaxOccupancy(),
                room.getPrice(),
                room.getTotalQuantity());
    }
}