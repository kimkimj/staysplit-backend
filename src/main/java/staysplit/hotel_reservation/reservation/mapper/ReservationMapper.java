package staysplit.hotel_reservation.reservation.mapper;

import org.springframework.stereotype.Component;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.reservation.dto.response.ParticipantDetailResponse;
import staysplit.hotel_reservation.reservation.dto.response.ReservationDetailResponse;
import staysplit.hotel_reservation.reservation.dto.response.ReservationListResponse;
import staysplit.hotel_reservation.reservation.dto.response.ReservedRoomDetailResponse;
import staysplit.hotel_reservation.reservation.domain.entity.ReservationEntity;
import staysplit.hotel_reservation.reservation.domain.entity.ReservationParticipantEntity;
import staysplit.hotel_reservation.reservedRoom.entity.ReservedRoomEntity;
import staysplit.hotel_reservation.room.domain.RoomEntity;

@Component
public class ReservationMapper {
    public ReservationDetailResponse toReservationDetailResponse(ReservationEntity reservation) {
        HotelEntity hotel = reservation.getReservedRooms().get(0).getRoom().getHotel();

        return ReservationDetailResponse.builder()
                .reservationId(reservation.getId())
                .reservationNumber(reservation.getReservationNumber())
                .reservationStatus(reservation.getStatus().toString())
                .checkIn(reservation.getCheckInDate())
                .checkOut(reservation.getCheckOutDate())
                .participants(reservation.getParticipants().stream().map(this::toParticipantDetailResponse).toList())
                .rooms(reservation.getReservedRooms().stream().map(this::toReservedRoomDetailResponse).toList())
                .pricePaid(reservation.getPricePaid())
                .totalPrice(reservation.getTotalPrice())
                .hotelName(hotel.getName())
                .hotelAddress(hotel.getAddress())
                .hotelMainImageUrl(null) // TODO: FIX
                .build();
    }

    // TODO: WARN: N +1
    public ParticipantDetailResponse toParticipantDetailResponse(ReservationParticipantEntity participant) {
        return ParticipantDetailResponse.builder()
                .email(participant.getCustomer().getUser().getEmail())
                .name(participant.getCustomer().getName())
                .splitAmount(participant.getSplitAmount())
                .paymentStatus(participant.getPaymentStatus().toString())
                .build();
    }

    public ReservedRoomDetailResponse toReservedRoomDetailResponse(ReservedRoomEntity reservedRoom) {

        return ReservedRoomDetailResponse.builder()
                .roomId(reservedRoom.getId())
                .roomType(reservedRoom.getRoom().getRoomType())
                .quantity(reservedRoom.getQuantity())
                .nights(reservedRoom.getNights())
                .pricePerNight(reservedRoom.getPricePerNight())
                .subTotal(reservedRoom.getSubtotalPrice())
                .build();
    }

    public ReservationListResponse toListResponse(ReservationEntity reservation) {
        ReservedRoomEntity firstRoom = reservation.getReservedRooms().get(0);
        RoomEntity room = firstRoom.getRoom();
        HotelEntity hotel = room.getHotel();

        return ReservationListResponse.builder()
                .reservationId(reservation.getId())
                .reservationNumber(reservation.getReservationNumber())
                .reservationStatus(reservation.getStatus().toString())
                .totalPrice(reservation.getTotalPrice())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .hotelName(hotel.getName())
                .hotelAddress(hotel.getAddress())
                .hotelMainImageUrl(null) // TODO: MAIN IMAGE
                .build();
    }


}
