package staysplit.hotel_reservation.room.domain.dto.request;

public record RoomCreateRequest (

        Long hotelId,
        String photoUrl,
        String description,
        String roomType,
        Integer price,
        Integer occupancy

){
}
