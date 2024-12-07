package dev.acacia.job_trucker.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // EL MANEJO DE ERRORES LO HAGO DESDE EL SERVICE, QUE A SU VEZ LLAMA AL GLOBAL
    // EXCEPTION HANDLER ///

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO); // Pasamos el DTO al servicio
        return ResponseEntity.ok("\n    User registered successfully");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id); // Pasamos el ID al servicio
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); // Llamamos al servicio
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updateUser = userService.updateUser(id, userDTO); // Pasamos el ID y el DTO al servicio y el resultado lo
                                                               // guardamos en updateUser
        Map<String, Object> response = new HashMap<>();
        response.put("MESSAGE:", "User updated successfully");
        response.put("user", updateUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Pasamos el ID al servicio
        return ResponseEntity.ok("\n    User deleted successfully");
    }

    @PostMapping("/login/{id}")
    public ResponseEntity<String> login(@PathVariable Long id, @RequestBody LoginDTO loginDTO) {
        boolean success = userService.login(id, loginDTO.getUserEmail(), loginDTO.getUserHashPass());
        if (success) {
            return ResponseEntity.ok("User logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("ERROR 401: UNAUTHORIZED. Email or Password isn't correct for this id");
    }



}