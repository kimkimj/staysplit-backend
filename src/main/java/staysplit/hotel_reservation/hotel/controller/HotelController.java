package staysplit.hotel_reservation.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.hotel.dto.request.*;
import staysplit.hotel_reservation.hotel.dto.response.CreateHotelResponse;
import staysplit.hotel_reservation.hotel.dto.response.GetHotelDetailResponse;
import staysplit.hotel_reservation.hotel.dto.response.GetHotelListResponse;
import staysplit.hotel_reservation.hotel.dto.response.UpdateHotelResponse;
import staysplit.hotel_reservation.hotel.service.HotelService;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    //호텔 생성
    @PostMapping("/")
    public Response<CreateHotelResponse> createhotel(@RequestBody CreateHotelRequest request){
        CreateHotelResponse createHotelResponse=hotelService.createHotel(request);
        return Response.success(createHotelResponse);
    }

    //호텔 수정
    @PatchMapping("/")
    public Response<UpdateHotelResponse> updatehotel(UpdateHotelRequest request){

        UpdateHotelResponse response=hotelService.updateHotel(request);
        return Response.success(response);
    }

    //호텔 상세 조회
    @GetMapping("/{hotelId}")
    public Response<GetHotelDetailResponse> getHotelDetail(@PathVariable GetHotelDetailRequest request){
        GetHotelDetailResponse response=hotelService.getHotelDetail(request);
        return Response.success(response);
    }


    //호텔 목록 조회
    @GetMapping("/list")
    public Response<List<GetHotelListResponse>> getHotelList(GetHotelListRequest request){
        List<GetHotelListResponse> response=hotelService.getHotelList(request);
        return Response.success(response);
    }


    //호텔 삭제
    @DeleteMapping("/{hotelId}")
    public Response deleteHotel(@PathVariable Long hotelId, @RequestParam Long providerId){

        DeleteHotelRequest request=new DeleteHotelRequest(hotelId, providerId);
        hotelService.deleteHotel(request);
        return Response.success("호텔이 삭제되었습니다.");

    }

}
