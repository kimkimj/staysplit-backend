package staysplit.hotel_reservation.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import staysplit.hotel_reservation.common.entity.Response;
import staysplit.hotel_reservation.like.domain.dto.request.AddHotelRequest;
import staysplit.hotel_reservation.like.domain.dto.request.CreateLikeListRequest;
import staysplit.hotel_reservation.like.domain.dto.request.ShareLikeListRequest;
import staysplit.hotel_reservation.like.domain.dto.request.UpdateLikeListRequest;
import staysplit.hotel_reservation.like.domain.dto.response.DeleteLikeListResponse;
import staysplit.hotel_reservation.like.domain.dto.response.GetLikeHotelResponse;
import staysplit.hotel_reservation.like.domain.dto.response.GetLikeShareResponse;
import staysplit.hotel_reservation.like.domain.entity.LikeListCustomerEntity;
import staysplit.hotel_reservation.like.domain.entity.LikeListEntity;
import staysplit.hotel_reservation.like.domain.entity.LikeListHotelEntity;
import staysplit.hotel_reservation.like.service.LikeListService;

import java.util.List;

@RestController
@RequestMapping("/api/likelist")
@RequiredArgsConstructor
public class LikeListController {
    private final LikeListService likeListService;

    @PostMapping
    public Response<LikeListEntity> createLikeList(@RequestBody CreateLikeListRequest req) {
        LikeListEntity result = likeListService.createLikeList(req.getCustomerId(), req.getListName());
        return Response.success(result);
    }

    @GetMapping("/customer/{customerId}")
    public Response<List<LikeListEntity>> getLikeListsByCustomer(@PathVariable Integer customerId) {
        List<LikeListEntity> lists = likeListService.getLikeListsByCustomer(customerId);
        return Response.success(lists);
    }

    @PutMapping("/{likeListId}")
    public Response<LikeListEntity> updateLikeListName(@PathVariable Integer likeListId,
                                                       @RequestBody UpdateLikeListRequest req) {
        LikeListEntity updated = likeListService.updateLikeListName(likeListId, req.getListName());
        return Response.success(updated);
    }

    @DeleteMapping("/{likeListId}")
    public Response<DeleteLikeListResponse> deleteLikeList(@PathVariable Integer likeListId) {
        DeleteLikeListResponse response = likeListService.deleteLikeList(likeListId);
        return Response.success(response);
    }

    @PostMapping("/{likeListId}/hotels")
    public Response<LikeListHotelEntity> addHotel(@PathVariable Integer likeListId,
                                                        @RequestBody AddHotelRequest req) {
        LikeListHotelEntity added = likeListService.addHotelToLikeList(likeListId, req.getHotelId(), req.getCustomerId());
        return Response.success(added);
    }

    @DeleteMapping("/{likeListId}/hotels/{hotelId}")
    public Response<String> removeLikeHotel(@PathVariable Integer likeListId, @PathVariable Integer hotelId) {
        likeListService.removeHotelFromLikeList(likeListId, hotelId);
        return Response.success("좋아요가 삭제되었습니다.");
    }

    @GetMapping("/{likeListId}/hotels")
    public Response<Page<GetLikeHotelResponse>> getHotels(@PathVariable Integer likeListId,
                                                          @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                          Pageable pageable) {
        Page<GetLikeHotelResponse> hotels = likeListService.getHotelsByLikeListId(likeListId, pageable);
        return Response.success(hotels);
    }

    @PostMapping("/{likeListId}/share")
    public Response<LikeListCustomerEntity> shareList(@PathVariable Integer likeListId,
                                                            @RequestBody ShareLikeListRequest req) {
        LikeListCustomerEntity shared = likeListService.shareLikeList(likeListId, req.getSharedCustomerId());
        return Response.success(shared);
    }

    @GetMapping("/shared/customer/{customerId}")
    public Response<Page<GetLikeShareResponse>> getSharedLists(@PathVariable Integer customerId,
                                                                 @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                                 Pageable pageable) {
        Page<GetLikeShareResponse> sharedLists = likeListService.getSharedListsByCustomer(customerId, pageable);
        return Response.success(sharedLists);
    }
}
