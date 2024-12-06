package dev.acacia.job_trucker.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private UserRepository userRepository;
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String customMessage = "ERROR 404: NOT FOUND. Can't perform this operation.";
        return new ResponseEntity<>(customMessage, HttpStatus.NOT_FOUND);  // 404 Not Found
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String customMessage = "ERROR: 500. An unexpected error occurred. Please try again later.";
        return new ResponseEntity<>(customMessage, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        String customMessage = "ERROR 404: USER NOT FOUND. This user doesn't exist.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
    }

    // Clase interna estática
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException() {
            super("User not found");
        }
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
