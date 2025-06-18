package staysplit.hotel_reservation.provider.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import staysplit.hotel_reservation.common.exception.AppException;
import staysplit.hotel_reservation.common.exception.ErrorCode;
import staysplit.hotel_reservation.provider.domain.dto.reqeust.ProviderSignupRequest;
import staysplit.hotel_reservation.provider.domain.dto.response.ProviderInfoResponse;
import staysplit.hotel_reservation.provider.domain.entity.ProviderEntity;
import staysplit.hotel_reservation.provider.repository.ProviderRepository;
import staysplit.hotel_reservation.user.domain.dto.entity.UserEntity;
import staysplit.hotel_reservation.user.domain.enums.Role;
import staysplit.hotel_reservation.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ProviderService providerService;

    private final String email = "provider@example.com";
    private final String rawPassword = "12345";
    private final String encodedPassword = "$2a$10$encodedPassword";

    private ProviderSignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new ProviderSignupRequest(email, rawPassword);
    }

    @Test
    void signup_successful_returnsProviderInfoResponse() {
        // given
        given(userRepository.existsByEmail(email)).willReturn(false);
        given(passwordEncoder.encode(rawPassword)).willReturn(encodedPassword);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        ArgumentCaptor<ProviderEntity> providerCaptor = ArgumentCaptor.forClass(ProviderEntity.class);

        // when
        ProviderInfoResponse response = providerService.signup(signupRequest);

        // then
        verify(userRepository).save(userCaptor.capture());
        verify(providerRepository).save(providerCaptor.capture());

        UserEntity savedUser = userCaptor.getValue();
        ProviderEntity savedProvider = providerCaptor.getValue();

        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(savedUser.getRole()).isEqualTo(Role.PROVIDER);
        assertThat(savedProvider.getUser()).isEqualTo(savedUser);

        assertThat(response.email()).isEqualTo(email);
    }

    @Test
    void signup_duplicateEmail_throwsAppException() {
        // given
        given(userRepository.existsByEmail(email)).willReturn(true);

        // expect
        assertThatThrownBy(() -> providerService.signup(signupRequest))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(ErrorCode.DUPLICATE_EMAIL.getMessage());

        verify(userRepository, never()).save(any());
        verify(providerRepository, never()).save(any());
    }
}
