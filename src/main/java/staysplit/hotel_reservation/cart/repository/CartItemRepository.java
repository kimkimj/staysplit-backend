package staysplit.hotel_reservation.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.cart.domain.CartEntity;
import staysplit.hotel_reservation.cart.domain.CartItemEntity;
import staysplit.hotel_reservation.room.domain.RoomEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    Optional<CartItemEntity> findByCartAndRoomAndCheckInDateAndCheckOutDate(
            CartEntity cart, RoomEntity room, LocalDate checkInDate, LocalDate checkOutDate);
}
