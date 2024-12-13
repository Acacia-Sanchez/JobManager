package dev.acacia.job_trucker.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") 
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "User registered successfully with role USER");
        response.put("user", registeredUser);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/register") 
    public ResponseEntity<Map<String, Object>> registerUserWithRole(@RequestBody UserDTO userDTO) {
        User registeredUserWithRole = userService.registerUserWithRole(userDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "User registered successfully with role " + userDTO.getUserRole());
        response.put("user", registeredUserWithRole);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/get/{id}") 
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/getAll") 
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); 
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO); 
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "User updated successfully");
        response.put("user", updatedUser);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); 
        return ResponseEntity.ok("\n    User deleted successfully");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/login")
    public ResponseEntity<String> login(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok("\n   User logged in successfully");
    }
    
}