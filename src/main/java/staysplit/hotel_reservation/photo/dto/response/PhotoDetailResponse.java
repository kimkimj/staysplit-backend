package staysplit.hotel_reservation.photo.dto.response;

import staysplit.hotel_reservation.photo.domain.PhotoEntity;

public record PhotoDetailResponse(
        Integer photoId,
        String displayType,
        String uploadedFileName,
        String savedFileName
) {
    public static PhotoDetailResponse from(PhotoEntity photo) {
        return new PhotoDetailResponse(photo.getId(), photo.getDisplayType().toString(),
                photo.getUploadFileName(), photo.getStoredFileName());
    }
}
