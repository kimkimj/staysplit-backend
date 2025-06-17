package staysplit.hotel_reservation.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.domain.customer.domain.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Customer findBySocialId(String socialId);

    boolean existsBySocialId(String socialId);
}
