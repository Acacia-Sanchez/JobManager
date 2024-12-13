package dev.acacia.job_trucker.user;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserPrincipalTest {
   @Test
    void testGetAuthorities() {
        User user = new User();
        user.setUserRole(UserRole.ADMIN);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword() {
        User user = new User();
        user.setUserHashPass("password");
        UserPrincipal userPrincipal = new UserPrincipal(user);
        String password = userPrincipal.getPassword();
        assertEquals("password", password);
    }

    @Test
    void testGetUsername() {
        User user = new User();
        user.setUserEmail("email");
        UserPrincipal userPrincipal = new UserPrincipal(user);
        String username = userPrincipal.getUsername();
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