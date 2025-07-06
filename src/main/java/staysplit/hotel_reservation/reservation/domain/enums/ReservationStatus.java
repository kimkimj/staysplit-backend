package staysplit.hotel_reservation.reservation.domain.enums;

public enum ReservationStatus {
    WAITING_PAYMENT, // 결제 미완료, 임시 예약 (재고 확보)
    CONFIRMED, // 결제 완료
    CANCELLED, // 예약 취소
    COMPLETE // 이용 완료
}
