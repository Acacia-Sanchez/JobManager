package dev.acacia.job_trucker.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserPrincipalTest {
   @Test
    void testGetAuthorities() {
        // Arrange
        User user = new User();
        user.setUserRole(UserRole.ADMIN);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        // Arrange
        User user = new User();
        user.setUserHashPass("password");
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        String password = userPrincipal.getPassword();

        // Assert
        assertEquals("password", password);
    }

    @Test
    void testGetUsername() {
        // Arrange
        User user = new User();
        user.setUserEmail("email");
        UserPrincipal userPrincipal = new UserPrincipal(user);

        // Act
        String username = userPrincipal.getUsername();

        // Assert
        assertEquals("email", username);
    }

    @Test
    void testIsAccountNonExpired() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);
        assertTrue(userPrincipal.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);
        assertTrue(userPrincipal.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);
        assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);
        assertTrue(userPrincipal.isEnabled());
    }
}
