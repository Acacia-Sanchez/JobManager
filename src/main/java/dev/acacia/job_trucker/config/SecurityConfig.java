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

    private AuthenticationEntryPoint authenticationEntryPoint;
    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint){
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .exceptionHandling(exceptions -> exceptions  
                .authenticationEntryPoint(authenticationEntryPoint))
            .authorizeHttpRequests(authz -> authz  
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")   
                            .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/api/offer/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/api/register").permitAll()  
                            .anyRequest().authenticated()  
            )
            .httpBasic(withDefaults());  
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}