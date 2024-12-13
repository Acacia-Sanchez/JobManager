package dev.acacia.job_trucker.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDTOTest {

    @Test
    void testGetUserName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("John Doe");
        assertEquals("John Doe", userDTO.getUserName());
    }

    @Test
    void testGetUserAddress() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserAddress("123 Main St");
        assertEquals("123 Main St", userDTO.getUserAddress());
    }

    @Test
    void testGetUserPhone() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserPhone("123-456-7890");
        assertEquals("123-456-7890", userDTO.getUserPhone());
    }

    @Test
    void testGetUserHashPass() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserHashPass("password123");
        assertEquals("password123", userDTO.getUserHashPass());
    }

    @Test
    void testGetUserEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", userDTO.getUserEmail());
    }

    @Test
    void testGetUserRole() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserRole(UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, userDTO.getUserRole());
    }

    @Test
    void testSetUserName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Jane Doe");
        assertEquals("Jane Doe", userDTO.getUserName());
    }

    @Test
    void testSetUserAddress() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserAddress("456 Elm St");
        assertEquals("456 Elm St", userDTO.getUserAddress());
    }

    @Test
    void testSetUserPhone() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserPhone("987-654-3210");
        assertEquals("987-654-3210", userDTO.getUserPhone());
    }

    @Test
    void testSetUserHashPass() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserHashPass("newpassword");
        assertEquals("newpassword", userDTO.getUserHashPass());
    }

    @Test
    void testSetUserEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail("janedoe@example.com");
        assertEquals("janedoe@example.com", userDTO.getUserEmail());
    }

    @Test
    void testSetUserRole() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserRole(UserRole.USER);
        assertEquals(UserRole.USER, userDTO.getUserRole());
    }
}