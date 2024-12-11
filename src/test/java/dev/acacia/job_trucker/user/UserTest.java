package dev.acacia.job_trucker.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {

    UserRole userRole = UserRole.USER; // Assuming UserRole is an enum

    @Test
    public void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setUserName("johnDoe");
        user.setUserHashPass("password123");
        user.setUserEmail("johndoe@example.com");
        user.setUserRole(userRole);

        assertEquals(1L, user.getId());
        assertEquals("johnDoe", user.getUserName());
        assertEquals("password123", user.getUserHashPass());
        assertEquals("johndoe@example.com", user.getUserEmail());
        assertEquals(UserRole.USER, user.getUserRole());
        assertEquals("USER", user.getUserRole().toString());
    }

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUserName("johnDoe");
        user.setUserHashPass("password123");
        user.setUserEmail("johndoe@example.com");
        user.setUserRole(userRole);

        String expected = "User{id=1, username='johnDoe', password='password123', email='johndoe@example.com', role='USER'}";
        assertEquals(expected, user.toString());
    }

    @Test
    public void testEquals() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUserName("johnDoe");
        user1.setUserHashPass("password123");
        user1.setUserEmail("johndoe@example.com");
        user1.setUserRole(userRole);

        User user2 = new User();
        user2.setId(1L);
        user2.setUserName("johnDoe");
        user2.setUserHashPass("password123");
        user2.setUserEmail("johndoe@example.com");
        user2.setUserRole(userRole);

        assertFalse(user1.equals(user2));
    }

    @Test
    public void testNotEquals() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUserName("johnDoe");
        user1.setUserHashPass("password123");
        user1.setUserEmail("johndoe@example.com");
        user1.setUserRole(userRole);

        User user2 = new User();
        user2.setId(2L);
        user2.setUserName("janeDoe");
        user2.setUserHashPass("password456");
        user2.setUserEmail("janedoe@example.com");
        user2.setUserRole(userRole);

        assertFalse(user1.equals(user2));
    }

}