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
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.EmailAlreadyExistsException;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.UserNotFoundException;
import dev.acacia.job_trucker.offer.OfferRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OfferRepository offerRepository;

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
    public void testRegisterUser_EmailAlreadyExists_ThrowsEmailAlreadyExistsException() {
        // Configuración
        UserDTO userDTO = new UserDTO();
        when(userRepository.existsByUserEmail(userDTO.getUserEmail())).thenReturn(true);

        // Ejecución
        try {
            userService.registerUser(userDTO);
            fail("Debería haber lanzado una EmailAlreadyExistsException");
        } catch (EmailAlreadyExistsException e) {
            // Verificación
            assertEquals("\n   ERROR: The email address is already in use. Please choose another one.", e.getMessage());
        }
    }

    @Test
    public void testRegisterUser_RolUser_RegistraUsuario() {
        // Configuración
        UserDTO userDTO = new UserDTO();
        when(passwordEncoder.encode(userDTO.getUserHashPass())).thenReturn("passwordEncriptado");

        // Ejecución
        User user = userService.registerUser(userDTO);

        // Verificación
        assertNotNull(user);
        assertEquals(userDTO.getUserName(), user.getUserName());
        assertEquals(userDTO.getUserAddress(), user.getUserAddress());
        assertEquals(userDTO.getUserPhone(), user.getUserPhone());
        assertEquals("passwordEncriptado", user.getUserHashPass());
        assertEquals(userDTO.getUserEmail(), user.getUserEmail());
        assertEquals(UserRole.USER, user.getUserRole());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUser() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.existsById(user.getId())).thenReturn(true);

        // Act
        User result = userService.getUser(user.getId());

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetUser_ThrowsUserNotFoundException() {
        // Arrange

        // Act and Assert
        assertThrows(GlobalExceptionHandler.UserNotFoundException.class, () -> userService.getUser(user.getId()));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testGetAllUsers_ThrowsNoUsersFoundException() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act and Assert
        assertThrows(GlobalExceptionHandler.NoUsersFoundException.class, () -> userService.getAllUsers());
    }

    @Test
    public void testDeleteUser_UsuarioNoExiste_ThrowsUserNotFoundException() {
        // Configuración
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        // Ejecución
        try {
            userService.deleteUser(id);
            fail("Debería haber lanzado una UserNotFoundException");
        } catch (UserNotFoundException e) {
            // Verificación
            assertEquals("\n      ERROR 404: User not found", e.getMessage());
        }
    }

    @Test
    public void testDeleteUser_UsuarioExiste_BorraUsuario() {
        // Configuración
        Long id = 1L;
        User user = new User("nombre", "dirección", "telefono", "password", "email@example.com", UserRole.USER);
        when(userRepository.existsById(id)).thenReturn(true);

        // Ejecución
        userService.deleteUser(id);

        // Verificación
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateUser_ThrowsUserNotFoundException() {
        // Arrange
        UserDTO userDTO = new UserDTO();

        // Act and Assert
        assertThrows(GlobalExceptionHandler.UserNotFoundException.class,
                () -> userService.updateUser(user.getId(), userDTO));
    }

    @Test
    void testLogin_ThrowsUserNotFoundException() {
        // Act and Assert
        assertThrows(GlobalExceptionHandler.UserNotFoundException.class,
                () -> userService.login(user.getId(), user.getUserEmail(), user.getUserHashPass()));
    }
}