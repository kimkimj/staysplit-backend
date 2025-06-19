package staysplit.hotel_reservation.hotel.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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


    //생성(관리자 권한)
    @Transactional
    public CreateHotelResponse createHotel(CreateHotelRequest request){

        //프로바이더인지 확인
        ProviderEntity provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 사용자는 Provider가 아닙니다."));



        HotelEntity hotel = HotelEntity.builder()
                //.hotelId(request.getHotleId())
                .provider(provider)
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .rating(request.getRating())
                .build();

        //hotel.createHotel(request);

        HotelEntity savedHotel=hotelRepository.save(hotel);
        return CreateHotelResponse.toDto(savedHotel);
    }


    //조회(낱개)
    @Transactional
    public GetHotelDetailResponse getHotelDetail(GetHotelDetailRequest request){
        HotelEntity hotel = hotelRepository.findByHotelId(request.getHotelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return GetHotelDetailResponse.toDto(hotel);
    }


    //전체 조회
    @Transactional
    public List<GetHotelListResponse> getHotelList(GetHotelListRequest request){

        //전체 호텔에 대한 리스트를 가져오기.
        List<HotelEntity> hotelList=hotelRepository.findAllByHotelId(request.getHotelId());

        //DTO로 변환 후 반환
        return hotelList.stream()
               .map(GetHotelListResponse::toDto)
                .collect(Collectors.toList());
    }

    //수정(관리자 권한)
    @Transactional
    public UpdateHotelResponse updateHotel(UpdateHotelRequest request){

        ProviderEntity provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 사용자는 Provider가 아닙니다."));

        HotelEntity hotel = hotelRepository.findByHotelId(request.getHotelId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!hotel.getProvider().getId().equals(provider.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "이 호텔을 수정할 권한이 없습니다.");
        }

        hotel.updateHotel(request);
        hotelRepository.save(hotel);
        return UpdateHotelResponse.toDto(hotel);
    }



    @Transactional
    public void deleteHotel(DeleteHotelRequest request){

        ProviderEntity provider = providerRepository.findById(request.getProviderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 사용자는 Provider가 아닙니다."));

        HotelEntity hotel = hotelRepository.findByHotelId(request.getHotelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!hotel.getProvider().getId().equals(provider.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "이 호텔을 삭제할 권한이 없습니다.");
        }

        hotelRepository.delete(hotel);
    }

}
