package dev.acacia.job_trucker.exceptions;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String customMessage = "ERROR 404: NO USERS FOUND.";
        response.getWriter().write("\n     ERROR 401: UNAUTHORIZED. You must write your credentials or be logged.");
    }
    
   
}