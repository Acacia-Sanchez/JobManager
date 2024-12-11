package dev.acacia.job_trucker.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testRegisterUser() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        User registeredUser = new User("John Doe", "johndoe@example.com", "password", null, null, null);
        when(userService.registerUser(userDTO)).thenReturn(registeredUser);

        // Act
        ResponseEntity<Map<String, Object>> response = userController.registerUser(userDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully with role USER", response.getBody().get("MESSAGE"));
    }

    @Test
    void testRegisterUserWithRole() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserRole(UserRole.ADMIN); // Asignar un rol a userDTO
        User registeredUserWithRole = new User("John Doe", "johndoe@example.com", "password", "ADMIN", null, null);
        when(userService.registerUserWithRole(userDTO)).thenReturn(registeredUserWithRole);

        // Act
        ResponseEntity<Map<String, Object>> response = userController.registerUserWithRole(userDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully with role ADMIN", response.getBody().get("MESSAGE"));
    }

    @Test
    void testGetUser() {
        // Arrange
        Long id = 1L;
        User user = new User("John Doe", "johndoe@example.com", "password", null, null, null);
        when(userService.getUser(id)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUser(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(new User("John Doe", "johndoe@example.com", "password", null, null, null));
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        User updatedUser = new User("John Doe", "johndoe@example.com", "newpassword", null, null, null);
        when(userService.updateUser(id, userDTO)).thenReturn(updatedUser);

        // Act
        ResponseEntity<Map<String, Object>> response = userController.updateUser(id, userDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody().get("MESSAGE"));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long id = 1L;
        doNothing().when(userService).deleteUser(id);

        // Act
        ResponseEntity<String> response = userController.deleteUser(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\n    User deleted successfully", response.getBody());
    }

    @Test
    void testLogin() {
        // Arrange
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("johndoe@example.com");

        // Act
        ResponseEntity<String> response = userController.login(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\n   User logged in successfully", response.getBody());
    }
}