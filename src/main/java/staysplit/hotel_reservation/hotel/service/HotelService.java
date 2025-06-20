package staysplit.hotel_reservation.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import staysplit.hotel_reservation.hotel.dto.request.*;
import staysplit.hotel_reservation.hotel.dto.response.CreateHotelResponse;
import staysplit.hotel_reservation.hotel.dto.response.GetHotelDetailResponse;
import staysplit.hotel_reservation.hotel.dto.response.GetHotelListResponse;
import staysplit.hotel_reservation.hotel.dto.response.UpdateHotelResponse;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final ProviderRepository providerRepository;

    public CreateHotelResponse createHotel(CreateHotelRequest request) {
        ProviderEntity provider = getProviderOrThrow(request.providerId());

        HotelEntity hotel = HotelEntity.builder()
                .provider(provider)
                .name(request.name())
                .address(request.address())
                .description(request.description())
                .starLevel(request.starLevel())
                .rating(request.rating())
                .imageUrl(request.imageUrl())
                .build();

        provider.addHotel(hotel);
        hotelRepository.save(hotel);

        return CreateHotelResponse.toDto(hotel);
    }

    // 호텔 상세 조회
    @Transactional(readOnly = true)
    public GetHotelDetailResponse getHotelDetail(GetHotelDetailRequest request) {
        HotelEntity hotel = getHotelOrThrow(request.hotelId());
        return GetHotelDetailResponse.toDto(hotel);
    }

    // 호텔 목록 조회
    @Transactional(readOnly = true)
    public List<GetHotelListResponse> getHotelList(GetHotelListRequest request) {
        List<HotelEntity> hotels = hotelRepository.findAllByHotelId(request.hotelId());
        return hotels.stream()
                .map(GetHotelListResponse::toDto)
                .collect(Collectors.toList());
    }

    // 호텔 정보 수정
    public UpdateHotelResponse updateHotel(UpdateHotelRequest request) {
        ProviderEntity provider = getProviderOrThrow(request.providerId());
        HotelEntity hotel = getHotelOrThrow(request.hotelId());

        verifyOwnership(hotel, provider);

        hotel.updateHotel(request);
        return UpdateHotelResponse.toDto(hotel);
    }

    // 호텔 삭제
    public void deleteHotel(DeleteHotelRequest request) {
        ProviderEntity provider = getProviderOrThrow(request.providerId());
        HotelEntity hotel = getHotelOrThrow(request.hotelId());

        verifyOwnership(hotel, provider);
        hotelRepository.delete(hotel);
    }

    private ProviderEntity getProviderOrThrow(Long providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "해당 사용자는 Provider가 아닙니다."));
    }

    private HotelEntity getHotelOrThrow(Long hotelId) {
        return hotelRepository.findByHotelId(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "호텔을 찾을 수 없습니다."));
    }

    private void verifyOwnership(HotelEntity hotel, ProviderEntity provider) {
        if (!hotel.getProvider().getId().equals(provider.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이 호텔에 대한 권한이 없습니다.");
        }
    }
}
