package staysplit.hotel_reservation.room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.room.domain.dto.request.RoomCreateRequest;
import staysplit.hotel_reservation.room.domain.dto.response.RoomDeleteResponse;
import staysplit.hotel_reservation.room.domain.dto.response.RoomInfoResponse;
import staysplit.hotel_reservation.room.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public Response<RoomInfoResponse> createRoom(@RequestBody RoomCreateRequest request, Authentication authentication) {
        RoomInfoResponse infoResponse = roomService.createRoom(authentication.getName(), request);
        return Response.success(infoResponse);
    }

    @GetMapping("/{roomId}")
    public Response<RoomInfoResponse> getRoomById(@PathVariable Long roomId) {
        RoomInfoResponse roomInfoResponse = roomService.findRoomById(roomId);
        return Response.success(roomInfoResponse);
    }

    @DeleteMapping("/{roomId}")
    public Response<RoomDeleteResponse> deleteRoom(@PathVariable Long roomId, Authentication authentication) {
        RoomDeleteResponse response = roomService.deleteRoom(authentication.getName(), roomId);
        return Response.success(response);
    }

    //
    // TODO: HotelController에 옮기고, SecConfig에서 uri 열어두기
    // GET ALL ROOMS
    @GetMapping("/hotels/{hotelId}")
    public Response<Page<RoomInfoResponse>> findAllRoomsByHotelId(@PathVariable Long hotelId,
                                                                  @PageableDefault(size = 10, sort = "price", direction = Sort.Direction.ASC)
                                                                  Pageable pageable) {
        Page<RoomInfoResponse> rooms = roomService.findAllRoomsByHotel(hotelId, pageable);
        return Response.success(rooms);
    }
}
