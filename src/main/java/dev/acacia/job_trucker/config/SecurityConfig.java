package dev.acacia.job_trucker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import dev.acacia.job_trucker.exceptions.CustomAuthenticationEntryPoint;

import org.springframework.security.core.userdetails.User;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF para toda la app, ya que al ser un API no es necesario

            .exceptionHandling(exceptions -> exceptions  // Configura el punto de entrada de autenticación personalizado
                .authenticationEntryPoint(customAuthenticationEntryPoint))
            
            .authorizeHttpRequests(authz -> authz  // Qué puntos de entrada pueden acceder sin autenticación y cuales no
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Solo para admin (para los Get users)
                            .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // además admin puede tb a todo lo de user
                            .requestMatchers("/api/register").permitAll()  // Permitir el acceso sin autenticación al registro
                            .anyRequest().authenticated()  // Requiere autenticación para otras rutas
            )
            .httpBasic(withDefaults());  // usar autenticación básica
            
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Definimos dos usuarios: uno con el rol USER y otro con el rol ADMIN
            if (username.equals("xanina")) {
                return User.withUsername("xanina")
                        .password(passwordEncoder().encode("1234Auth"))
                        .roles("USER") // Rol USER
                        .build();
            } else if (username.equals("admin")) {
                return User.withUsername("admin")
                        .password(passwordEncoder().encode("adminPass"))
                        .roles("ADMIN") // Rol ADMIN
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        }
    };
}


    // Configuración del encoder de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para cifrar las contraseñas
    }
}
