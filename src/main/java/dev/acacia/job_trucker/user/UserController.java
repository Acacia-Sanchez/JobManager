package dev.acacia.job_trucker.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // EL MANEJO DE ERRORES LO HAGO DESDE EL SERVICE, QUE A SU VEZ LLAMA AL GLOBAL
    // EXCEPTION HANDLER ///

    @PostMapping("/register") // acceso público, siempre ROL USER
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO); // Pasamos el DTO al servicio
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "User registered successfully with role USER");
        response.put("user", registeredUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register") // solo el admin puede elegir entre generar ADMIN o USER
    public ResponseEntity<Map<String, Object>> registerUserWithRole(@RequestBody UserDTO userDTO) {
        User registeredUserWithRole = userService.registerUserWithRole(userDTO); // Pasamos el DTO al servicio
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "User registered successfully with role " + userDTO.getUserRole());
        response.put("user", registeredUserWithRole);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/get/{id}") // solo el admin puede ver los users
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id); // Pasamos el ID al servicio
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/getAll") // solo el admin puede ver los users
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // Llamamos al servicio
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/user/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO); // Pasamos el ID y el DTO al servicio y el resultado lo
                                                                // guardamos en updateUser
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE", "User updated successfully");
        response.put("user", updatedUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Pasamos el ID al servicio
        return ResponseEntity.ok("\n    User deleted successfully");
    }

    @PostMapping("/user/login/{id}")
    public ResponseEntity<String> login(@PathVariable Long id, @RequestBody LoginDTO loginDTO) {
        boolean success = userService.login(id, loginDTO.getUserEmail(), loginDTO.getUserHashPass());
        if (success) {
            return ResponseEntity.ok("\n   User logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("\n   ERROR 401: UNAUTHORIZED. Email or Password isn't correct for this id");
    }

    @DeleteMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Spring Security eliminar la sesión activa HTTP en el servidor
        new SecurityContextLogoutHandler().logout(request, response, null);
        return ResponseEntity.ok("\n   User logged out successfully");
    }

}