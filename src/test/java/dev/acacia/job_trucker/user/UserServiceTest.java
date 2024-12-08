package dev.acacia.job_trucker.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.NoUsersFoundException;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setUserName("Acacia Sánchez");
        user.setUserEmail("aca@gmail.com");
        user.setUserAddress("Ruedes, Gijòn");
        user.setUserPhone("627909745");
        user.setUserHashPass("1234Pass");
    }

    
    @Test
    void testDeleteUserWhenUserExists() {
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).existsById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }
    @Test
    void testDeleteUserWhenUserNotExists() {
        when(userRepository.existsById(user.getId())).thenReturn(false);

        assertThrows(GlobalExceptionHandler.UserNotFoundException.class, () -> userService.deleteUser(user.getId()));
        verify(userRepository, times(1)).existsById(user.getId());
        verify(userRepository, never()).deleteById(user.getId());
    }


    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(
            new User("acacia", "dirección aca", "6274 909 745", "1234pass", "aca@gmail.com"),
            new User("bouba", "dirección bb", "722683424", "4567pass", "bouba@gmail.com")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }


    @Test
    void testGetUser() {

    }

    @Test
    void testLogin() {
        when(userRepository.loginByuserIdAnduserEmailAnduserHashPass(user.getId(), user.getUserEmail(), user.getUserHashPass()))
            .thenReturn(Optional.of(user));  // usuario con las credenciales correctas
        
        boolean result = userService.login(user.getId(), user.getUserEmail(), user.getUserHashPass());
        assertTrue(result);
    }
/* 
    public boolean login(Long id, String userEmail, String userHashPass) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        
        // le paso userEmail y userHashPass al metodo del repository y lo que devuelve el método se guarda en el objeto user
        Optional<User> user = userRepository.loginByuserIdAnduserEmailAnduserHashPass(id, userEmail, userHashPass);
        if (user.isPresent()) {  
            logger.info("\n   Login successful for user: {}", userEmail);
            return true;
        }
    
        logger.warn("\n   Login failed for user: {}", userEmail);
        return false;
    }
    
 */
    @Test
    void testLogout() {
        when(!userRepository.existsById(user.getId())).thenReturn(false);
        assertThrows(GlobalExceptionHandler.UserNotFoundException.class, () -> userService.logout(user.getId(), user.getUserEmail()));
    }

    @Test
    void testRegisterUser() {

    }

    @Test
    void testUpdateUser() {

    }
}
