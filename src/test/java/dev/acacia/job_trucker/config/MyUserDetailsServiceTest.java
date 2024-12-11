package dev.acacia.job_trucker.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.acacia.job_trucker.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUserEmail("username")).thenReturn(null);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername("username"));
    }

    @Test
    void testLoadUserByUsername_NullInput() {
        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername(null));
    }
}