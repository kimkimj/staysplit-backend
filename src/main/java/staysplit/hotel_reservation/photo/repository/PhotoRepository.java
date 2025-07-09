package staysplit.hotel_reservation.photo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.photo.domain.PhotoEntity;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Integer> {
    Optional<PhotoEntity> findByStoredFileName(String storedFileName);
}
