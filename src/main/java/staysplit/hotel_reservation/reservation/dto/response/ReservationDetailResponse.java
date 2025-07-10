package staysplit.hotel_reservation.reservation.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ReservationDetailResponse(
    Integer reservationId,
    String reservationNumber,
    String reservationStatus,
    LocalDate checkIn,
    LocalDate checkOut,
    Integer nights,
    List<ParticipantDetailResponse> participants,
    Integer totalPrice,
    Integer pricePaid,

    // 호텔 정보
    String hotelName,
    String hotelAddress,
    String hotelMainImageUrl,

    // 방 정보
    List<ReservedRoomDetailResponse> rooms

    ) { }
