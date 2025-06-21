package staysplit.hotel_reservation.hotel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;

public class HotelServiceTest {

    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private AutoCloseable closeable;

    private HotelEntity hotel;
    private ProviderEntity provider;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);


    }


    @Test
    void createHotelList_success() {

    }

    @Test
    void getHotelDetail_success() {


    }

    @Test
    void getHotelList_success() {


    }

    @Test
    void updateHotel_success() {


    }

    @Test
    void deleteHotel_success() {


    }

}
