package dev.acacia.job_trucker.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());
        User user = new User(
                userDTO.getUserName(),
                userDTO.getUserAddress(), 
                userDTO.getUserPhone(), 
                encodedPassword, 
                userDTO.getUserEmail()
        );
        return userRepository.save(user);
    }
    
}