package staysplit.hotel_reservation.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.user.domain.dto.entity.UserEntity;

import java.security.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
    boolean existsByUser(UserEntity user);
}
