package staysplit.hotel_reservation.hotel.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.customer.domain.dto.request.NicknameChangeRequest;
import staysplit.hotel_reservation.customer.domain.dto.response.CustomerInfoResponse;
import staysplit.hotel_reservation.customer.domain.entity.CustomerEntity;
import staysplit.hotel_reservation.customer.repository.CustomerRepository;
import staysplit.hotel_reservation.customer.service.CustomerService;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;
import staysplit.hotel_reservation.user.domain.dto.entity.UserEntity;
import staysplit.hotel_reservation.user.domain.enums.Role;
import staysplit.hotel_reservation.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
