package dev.acacia.job_trucker.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginDTOTest {

    @Test
    public void testGetUserEmail() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", loginDTO.getUserEmail());
    }

    @Test
    public void testGetUserHashPass() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserHashPass("password123");
        assertEquals("password123", loginDTO.getUserHashPass());
    }

    @Test
    public void testSetUserEmail() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", loginDTO.getUserEmail());
    }

    @Test
    public void testSetUserHashPass() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserHashPass("password123");
        assertEquals("password123", loginDTO.getUserHashPass());
    }

    @Test
    public void testBuilder() {
        LoginDTO loginDTO = LoginDTO.builder()
                .userEmail("johndoe@example.com")
                .userHashPass("password123")
                .build();
        assertEquals("johndoe@example.com", loginDTO.getUserEmail());
        assertEquals("password123", loginDTO.getUserHashPass());
    }
}