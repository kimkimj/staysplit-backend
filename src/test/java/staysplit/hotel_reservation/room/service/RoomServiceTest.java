package staysplit.hotel_reservation.room.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;
import staysplit.hotel_reservation.room.domain.RoomEntity;
import staysplit.hotel_reservation.room.domain.dto.request.RoomCreateRequest;
import staysplit.hotel_reservation.room.domain.dto.request.RoomModifyRequest;
import staysplit.hotel_reservation.room.domain.dto.response.RoomInfoResponse;
import staysplit.hotel_reservation.room.repository.RoomRepository;
import staysplit.hotel_reservation.user.domain.dto.entity.UserEntity;
import staysplit.hotel_reservation.user.domain.enums.LoginSource;
import staysplit.hotel_reservation.user.domain.enums.Role;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private HotelRepository hotelRepository;

    private ProviderEntity provider;
    private UserEntity user;
    private HotelEntity hotel;
    private RoomEntity room;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .email("user@example.com")
                .loginSource(LoginSource.LOCAL)
                .role(Role.PROVIDER)
                .password("password")
                .id(1L)
                .build();

        provider = ProviderEntity.builder()
                .user(user)
                .hotel(hotel)
                .build();

        hotel = HotelEntity.builder()
                .hotelId(1L)
                .build();

        hotel.setProvider(provider);

        room = RoomEntity.builder()
                .id(1L)
                .hotel(hotel)
                .roomType("Single Bed")
                .price(100)
                .description("Refund not allowed")
                .maxOccupancy(2)
                .photoUrl("photo.jpg")
                .build();
    }

    @Test
    void createRoom_success() {
        RoomCreateRequest request = new RoomCreateRequest(1L, "photo.jpg", "Nice room", "Single Bed", 100);

        when(providerRepository.findByEmail(provider.getUser().getEmail())).thenReturn(Optional.of(provider));
        when(roomRepository.save(any(RoomEntity.class))).thenReturn(room);

        RoomInfoResponse response = roomService.createRoom(provider.getUser().getEmail(), request);

        assertEquals("Deluxe", response.roomType());
        verify(roomRepository).save(any(RoomEntity.class));
    }

    @Test
    void modifyRoom_success() {
        RoomModifyRequest request = new RoomModifyRequest(1L, "photo.jpg", "no refund", "Updated", 150, 2);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(providerRepository.findByEmail(provider.getUser().getEmail())).thenReturn(Optional.of(provider));

        RoomInfoResponse response = roomService.modifyRoom(provider.getUser().getEmail(), request);

        assertEquals("Suite", response.roomType());
        assertEquals(150, response.price());
        verify(roomRepository, never()).save(any());
    }

    @Test
    void deleteRoom_success() {
        when(provider.getUser().getEmail()).thenReturn("provider@example.com");
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(providerRepository.findByEmail("provider@example.com")).thenReturn(Optional.of(provider));

        String result = roomService.deleteRoom("provider@example.com", 1L);

        assertEquals("방이 성공적으로 삭제되었습니다", result);
        verify(roomRepository).delete(room);
    }

    @Test
    void findRoomById_success() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        RoomInfoResponse response = roomService.findRoomById(1L);

        assertEquals(room.getId(), response.hotelId());
    }

}