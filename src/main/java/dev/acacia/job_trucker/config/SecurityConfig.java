package dev.acacia.job_trucker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 // creo que se pueden borrar estas 2 lineas de debajo ///////////////////////
 ////////////////////////////////////////////////////////////////////////////    
/*     @Autowired 
    private UserDetailsService userDetailsService; */

    private AuthenticationEntryPoint authenticationEntryPoint;
    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint){
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF para toda la app, ya que al ser un API no es necesario

            .exceptionHandling(exceptions -> exceptions  // Configura el punto de entrada de autenticación personalizado
                //.authenticationEntryPoint(customAuthenticationEntryPoint))
                .authenticationEntryPoint(authenticationEntryPoint))
            
            .authorizeHttpRequests(authz -> authz  // Qué puntos de entrada pueden acceder sin autenticación y cuales no
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Solo para admin (para Get y Delete endpoints)   
                            .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/api/offer/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/api/register").permitAll()  // Permite el acceso sin autenticación al registro
                            .anyRequest().authenticated()  // Requiere autenticación para otras rutas
            )
            .httpBasic(withDefaults());  // usar autenticación básica
        return http.build();
    }

    // Configuración del encoder de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para cifrar las contraseñas
    }

}