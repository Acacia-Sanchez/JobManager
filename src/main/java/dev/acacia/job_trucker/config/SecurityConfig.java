package dev.acacia.job_trucker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                            .requestMatchers("/api/user/register").permitAll()  // Permitir el acceso sin autenticación al registro
                            .anyRequest().authenticated()  // Requiere autenticación para otras rutas
            )
            .httpBasic(withDefaults());  // Configura la autenticación básica
            
        return http.build();
    }

    // Configuración de un usuario en memoria con rol "USER"
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return User.withUsername("xanina")
                        .password(passwordEncoder().encode("1234Auth"))  // Encripta la contraseña
                        .roles("USER")
                        .build();
            }
        };
    }

    // Configuración del encoder de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para cifrar las contraseñas
    }
}
