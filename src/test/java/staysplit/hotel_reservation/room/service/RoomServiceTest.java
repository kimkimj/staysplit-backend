package staysplit.hotel_reservation.room.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.hotel.entity.HotelEntity;
import staysplit.hotel_reservation.hotel.repository.HotelRepository;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.domain.dto.request.RoomCreateRequest;
import staysplit.hotel_reservation.room.domain.dto.response.RoomInfoResponse;
import staysplit.hotel_reservation.room.repository.RoomRepository;
import staysplit.hotel_reservation.user.domain.dto.entity.UserEntity;
import staysplit.hotel_reservation.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HotelRepository hotelRepository;

    private ProviderEntity mockProvider;
    private RoomEntity mockRoom;
    private HotelEntity mockHotel;
    private UserEntity mockUser;

    @BeforeEach
    void setUp() {
        mockHotel = HotelEntity.builder().hotelId(1L).build();
        mockProvider = ProviderEntity.builder().id(100L).hotel(mockHotel).build();
        mockUser = UserEntity.builder().id(100L).email("provider@test.com").build();
        mockRoom = RoomEntity.builder()
                .id(10L)
                .hotel(mockHotel)
                .roomType("Deluxe")
                .price(100)
                .description("Nice room")
                .photoUrl("photo.jpg")
                .maxOccupancy(2)
                .build();
        mockHotel.setProvider(mockProvider);
    }

    @Test
    void createRoom_success() {
        RoomCreateRequest request = new RoomCreateRequest(
                1L,
                "photo.jpg",
                "Nice room",
                "Deluxe",
                100,
                2
        );

        Mockito.when(userRepository.findByEmail("provider@test.com")).thenReturn(Optional.of(mockUser));
        Mockito.when(providerRepository.findById(100L)).thenReturn(Optional.of(mockProvider));
        Mockito.when(roomRepository.save(any(RoomEntity.class))).thenReturn(mockRoom);

        RoomInfoResponse response = roomService.createRoom("provider@test.com", request);

        assertEquals("Deluxe", response.roomType());
        assertEquals(100, response.price());
    }

    @Test
    void createRoom_fail_userNotFound() {
        RoomCreateRequest request = new RoomCreateRequest(
                1L,
                "photo.jpg",
                "Nice room",
                "Deluxe",
                100,
                2
        );

        Mockito.when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> roomService.createRoom("notfound@test.com", request));
    }

    @Test
    void deleteRoom_fail_unauthorized() {
        ProviderEntity otherProvider = ProviderEntity.builder().id(200L).hotel(mockHotel).build();
        mockHotel.setProvider(mockProvider);

        Mockito.when(roomRepository.findById(10L)).thenReturn(Optional.of(mockRoom));
        Mockito.when(userRepository.findByEmail("unauthorized@test.com"))
                .thenReturn(Optional.of(UserEntity.builder().id(200L).email("unauthorized@test.com").build()));
        Mockito.when(providerRepository.findById(200L)).thenReturn(Optional.of(otherProvider));

        assertThrows(AppException.class, () -> roomService.deleteRoom("unauthorized@test.com", 10L));
    }

    @Test
    void findRoomById_success() {
        Mockito.when(roomRepository.findById(10L)).thenReturn(Optional.of(mockRoom));

        RoomInfoResponse response = roomService.findRoomById(10L);

        assertEquals("Deluxe", response.roomType());
        assertEquals(100, response.price());
    }

    @Test
    void findRoomById_fail_notFound() {
        Mockito.when(roomRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> roomService.findRoomById(999L));
    }
}
