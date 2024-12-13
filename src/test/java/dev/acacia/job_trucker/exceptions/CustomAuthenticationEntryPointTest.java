package dev.acacia.job_trucker.exceptions;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.AuthenticationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomAuthenticationEntryPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    public void testCommence_IOException() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/api/protected");
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        doThrow(new IOException()).when(writer).write(any(String.class));
    
        // Act y Assert
        try {
            customAuthenticationEntryPoint.commence(request, response, authException);
            fail("Debería lanzar una IOException");
        } catch (IOException e) {
            // OK
        }
    }

    @Test
    public void testCommence_ServletException() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/api/protected");
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        doThrow(new IOException()).when(writer).write(any(String.class));
    
        // Act y Assert
        try {
            customAuthenticationEntryPoint.commence(request, response, authException);
            fail("Debería lanzar una IOException");
        } catch (IOException e) {
            // OK
        }
    }
}