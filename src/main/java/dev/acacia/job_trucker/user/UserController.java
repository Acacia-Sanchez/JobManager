package dev.acacia.job_trucker.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO); // Pasamos el DTO al servicio
        return ResponseEntity.ok("User registered successfully");
    }

/*     @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (id != null) {
            try {
                userService.deleteUser(id);   // Pasamos el ID al servicio
                return ResponseEntity.ok("User deleted successfully");
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());  // 404 Not Found
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());  // 500 Internal Server Error
            }
        } else {
            return ResponseEntity.badRequest().body("ERROR 404: Invalid ID. This user doesn't exist. Can't delete it.");
        }
    } */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);   // Pasamos el ID al servicio
        return ResponseEntity.ok("User deleted successfully");
    }
    



}