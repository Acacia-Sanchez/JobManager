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
        UserDTO userDTO = new UserDTO();
        User registeredUser = new User("John Doe", "johndoe@example.com", "password", null, null, null);
        when(userService.registerUser(userDTO)).thenReturn(registeredUser);
        ResponseEntity<Map<String, Object>> response = userController.registerUser(userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully with role USER", response.getBody().get("MESSAGE"));
    }

    @Test
    void testRegisterUserWithRole() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserRole(UserRole.ADMIN);
        User registeredUserWithRole = new User("John Doe", "johndoe@example.com", "password", "ADMIN", null, null);
        when(userService.registerUserWithRole(userDTO)).thenReturn(registeredUserWithRole);
        ResponseEntity<Map<String, Object>> response = userController.registerUserWithRole(userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully with role ADMIN", response.getBody().get("MESSAGE"));
    }

    @Test
    void testGetUser() {
        Long id = 1L;
        User user = new User("John Doe", "johndoe@example.com", "password", null, null, null);
        when(userService.getUser(id)).thenReturn(user);
        ResponseEntity<User> response = userController.getUser(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User("John Doe", "johndoe@example.com", "password", null, null, null));
        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<User>> response = userController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        User updatedUser = new User("John Doe", "johndoe@example.com", "newpassword", null, null, null);
        when(userService.updateUser(id, userDTO)).thenReturn(updatedUser);
        ResponseEntity<Map<String, Object>> response = userController.updateUser(id, userDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody().get("MESSAGE"));
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;
        doNothing().when(userService).deleteUser(id);
        ResponseEntity<String> response = userController.deleteUser(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\n    User deleted successfully", response.getBody());
    }

    @Test
    void testLogin() {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("johndoe@example.com");
        ResponseEntity<String> response = userController.login(authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("\n   User logged in successfully", response.getBody());
    }
}