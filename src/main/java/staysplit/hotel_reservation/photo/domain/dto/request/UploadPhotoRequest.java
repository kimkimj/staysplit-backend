package staysplit.hotel_reservation.photo.domain.dto.request;

// TODO:  LONG -> INTEGER
public record UploadPhotoRequest(
        String entityType, // hotel or room
        Integer entityId,
        String dirName
) {
}
