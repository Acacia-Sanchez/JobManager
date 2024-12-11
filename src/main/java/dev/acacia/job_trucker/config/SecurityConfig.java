package dev.acacia.job_trucker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import dev.acacia.job_trucker.exceptions.CustomAuthenticationEntryPoint;
import dev.acacia.job_trucker.user.UserController;

import org.springframework.security.core.userdetails.User;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    private AuthenticationEntryPoint authenticationEntryPoint;
    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint){
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

/*     private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    } */
    
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

/*     @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);


        return provider;
    } */


/*  HARDCODED FROM VIDEO TELUSKO
    @SuppressWarnings("deprecation")
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User
            .withDefaultPasswordEncoder()
            .username("kiran")
            .password("k123")
            .roles("USER")
            .build();
    
        UserDetails user2 = User
            .withDefaultPasswordEncoder()
            .username("harsh")
            .password("h123")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
 */
/*     @Bean
    public UserDetailsService userDetailsService() {
logger.debug("boubaca SECURITY_CONFIG: entrando en userdetailsservice");        
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // EN CUANTO HAYA CREADO UN ADMIN EN LA BBDD HAY QUE BORRAR ESTE
                if (username.equals("admin@gmail.com")) {
                    return User.withUsername("admin@gmail.com")  // usar el email como nombre de usuario
                            .password(passwordEncoder().encode("adminPass"))
                            .roles("ADMIN") // Rol ADMIN
                            .build();
                } else {
logger.debug("boubaca SECURITY_CONFIG: else user not found");

                    throw new UsernameNotFoundException("User not found");
                }
            }
        };
    }
 */


    // Configuración del encoder de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para cifrar las contraseñas
    }
}
