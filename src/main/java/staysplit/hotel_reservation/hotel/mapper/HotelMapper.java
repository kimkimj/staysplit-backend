package staysplit.hotel_reservation.hotel.mapper;

import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.hotel.dto.response.CreateHotelResponse;
import staysplit.hotel_reservation.hotel.dto.response.GetHotelDetailResponse;
import staysplit.hotel_reservation.hotel.dto.response.GetHotelListResponse;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.photo.domain.PhotoEntity;
import staysplit.hotel_reservation.photo.service.PhotoUrlBuilder;

import java.util.List;
import java.util.Optional;

@Component
public class HotelMapper {
    public GetHotelDetailResponse toDetailResponse(HotelEntity hotel, PhotoUrlBuilder urlBuilder) {

        Optional<PhotoEntity> mainPhoto = hotel.getMainPhoto();

        String mainUrl = mainPhoto.isPresent() ? mainPhoto.get().buildFullUrl(urlBuilder) : null;
        List<String> additionalUrls = hotel.getPhotos()
                .stream()
                .filter(photo -> !photo.isMainPhoto())
                .map(photo -> photo.buildFullUrl(urlBuilder))
                .toList();

        return new GetHotelDetailResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getLongitude(),
                hotel.getLatitude(),
                hotel.getDescription(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                mainUrl,
                additionalUrls
        );
    }

    public GetHotelListResponse toListResponse(HotelEntity hotel, PhotoUrlBuilder urlBuilder) {
        Optional<PhotoEntity> mainPhoto = hotel.getMainPhoto();

        String mainUrl = mainPhoto.isPresent() ? mainPhoto.get().buildFullUrl(urlBuilder) : null;

        return new GetHotelListResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getStarLevel(),
                hotel.getRating(),
                hotel.getReviewCount(),
                mainUrl
        );
    }

    public CreateHotelResponse toCreateHotelResponse(HotelEntity hotel) {
        return new CreateHotelResponse(
                hotel.getHotelId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getDescription(),
                hotel.getStarLevel()
        );
    }
}
