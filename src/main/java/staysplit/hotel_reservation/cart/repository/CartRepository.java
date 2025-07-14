package staysplit.hotel_reservation.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.cart.domain.CartEntity;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findByCustomer(CustomerEntity customer);
}
