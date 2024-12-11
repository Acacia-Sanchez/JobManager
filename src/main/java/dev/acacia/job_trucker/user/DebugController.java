package dev.acacia.job_trucker.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register/debug")
public class DebugController {

    private final UserRepository userRepository;

    public DebugController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*     @GetMapping("/findUserByEmail")  // poner un email que ya exista en la bbdd y lo muestra
    public ResponseEntity<User> findUserByEmail(@RequestParam String email) {
        return ResponseEntity.of(userRepository.findByUserEmail(email));
    } */

    
}