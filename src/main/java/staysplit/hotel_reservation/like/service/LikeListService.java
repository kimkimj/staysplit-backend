package staysplit.hotel_reservation.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.repository.CustomerRepository;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;
import staysplit.hotel_reservation.like.domain.dto.response.DeleteLikeListResponse;
import staysplit.hotel_reservation.like.domain.dto.response.GetLikeHotelResponse;
import staysplit.hotel_reservation.like.domain.dto.response.GetLikeShareResponse;
import staysplit.hotel_reservation.like.domain.entity.LikeListCustomerEntity;
import staysplit.hotel_reservation.like.domain.entity.LikeListEntity;
import staysplit.hotel_reservation.like.domain.entity.LikeListHotelEntity;
import staysplit.hotel_reservation.like.repository.LikeListCustomerRepository;
import staysplit.hotel_reservation.like.repository.LikeListHotelRepository;
import staysplit.hotel_reservation.like.repository.LikeListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeListService {
    private final LikeListRepository likeListRepository;
    private final LikeListHotelRepository likeListHotelRepository;
    private final LikeListCustomerRepository likeListCustomerRepository;
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;

    public LikeListEntity createLikeList(Integer customerId, String listName) {
        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                ErrorCode.USER_NOT_FOUND.getMessage()));

        LikeListEntity likeList = LikeListEntity.builder()
                .customer(customer)
                .listName(listName)
                .build();
        return likeListRepository.save(likeList);
    }

    public List<LikeListEntity> getLikeListsByCustomer(Integer customerId) {
        return likeListRepository.findByCustomerId(customerId);
    }

    public LikeListEntity updateLikeListName(Integer likeListId, String newName) {
        LikeListEntity likeList = likeListRepository.findById(likeListId)
                .orElseThrow(() -> new RuntimeException("LikeList not found"));
        likeList.setListName(newName);
        return likeListRepository.save(likeList);
    }

    public DeleteLikeListResponse deleteLikeList(Integer likeListId) {
        likeListCustomerRepository.deleteByLikeListId(likeListId);
        likeListHotelRepository.deleteByLikeListId(likeListId);
        likeListRepository.deleteById(likeListId);

        return new DeleteLikeListResponse("좋아요 리스트가 성공적으로 삭제되었습니다.", likeListId);
    }

    public LikeListHotelEntity addHotelToLikeList(Integer likeListId, Integer hotelId, Integer customerId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                ErrorCode.HOTEL_NOT_FOUND.getMessage()));

        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                ErrorCode.USER_NOT_FOUND.getMessage()));

        LikeListEntity likeList = likeListRepository.findById(likeListId)
                .orElseThrow(() -> new RuntimeException("LikeList not found"));

        LikeListHotelEntity likeListHotel = LikeListHotelEntity.builder()
                .likeList(likeList)
                .hotel(hotel)
                .customer(customer)
                .build();

        return likeListHotelRepository.save(likeListHotel);
    }

    public void removeHotelFromLikeList(Integer likeListId, Integer hotelId) {
        likeListHotelRepository.deleteByLikeListIdAndHotelId(likeListId, hotelId);
    }

    public Page<GetLikeHotelResponse> getHotelsByLikeListId(Integer likeListId, Pageable pageable) {
        return likeListHotelRepository.findByLikeListId(likeListId, pageable);
    }

    public LikeListCustomerEntity shareLikeList(Integer likeListId, Integer sharedCustomerId) {
        CustomerEntity sharedCustomer = customerRepository.findById(sharedCustomerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND,
                ErrorCode.USER_NOT_FOUND.getMessage()));

        LikeListEntity likeList = likeListRepository.findById(likeListId)
                .orElseThrow(() -> new RuntimeException("LikeList not found"));

        LikeListCustomerEntity share = LikeListCustomerEntity.builder()
                .likeList(likeList)
                .customer(sharedCustomer)
                .build();
        return likeListCustomerRepository.save(share);
    }

    public Page<GetLikeShareResponse> getSharedListsByCustomer(Integer customerId, Pageable pageable) {
        return likeListCustomerRepository.findByCustomerId(customerId, pageable);
    }
}
