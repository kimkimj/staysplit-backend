package staysplit.hotel_reservation.photo.dto.response;

import staysplit.hotel_reservation.photo.domain.PhotoEntity;

public record PhotoDetailResponse(
        Integer photoId,
        String displayType,
        String uploadedFileName,
        String savedFileName
) {
    public static PhotoDetailResponse from(PhotoEntity photo, String displayType) {
        return new PhotoDetailResponse(photo.getId(), displayType,
                photo.getUploadFileName(), photo.getStoredFileName());
    }
}
