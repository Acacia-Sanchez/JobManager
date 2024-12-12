package dev.acacia.job_trucker.user;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
// esta clase refiere al usuario actual, el que está intentando acceder / logarse

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devolver solo el rol del usuario con el prefijo "ROLE_"
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));
    }
    
    @Override
    public String getPassword() {
        return user.getUserHashPass();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail(); // <------
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

}