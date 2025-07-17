package staysplit.hotel_reservation.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {

    Optional<HotelEntity> findById(Integer hotelId);

    //위치기반
    @Query("""
    SELECT DISTINCT h
    FROM HotelEntity h
    JOIN FETCH h.rooms r
    LEFT JOIN FETCH r.reservations
    WHERE h.latitude BETWEEN :minLat AND :maxLat
      AND h.longitude BETWEEN :minLon AND :maxLon
""")
    List<HotelEntity> searchByLocation(    @Param("minLat") double minLat,
                                           @Param("maxLat") double maxLat,
                                           @Param("minLon") double minLon,
                                           @Param("maxLon") double maxLon
                                        );

}
