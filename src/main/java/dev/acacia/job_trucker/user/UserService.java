package dev.acacia.job_trucker.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.NoUsersFoundException;

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

    public void deleteUser(Long id) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException(); 
        }
        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoUsersFoundException();
        }
        return users;
    }

    public void updateUser(Long id, UserDTO userDTO) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
    
        // Obtener el usuario existente
        User user = getUser(id);
    
        // Actualizar campos solo si no son nulos
        if (userDTO.getUserName() != null) {
            user.setUserName(userDTO.getUserName());
        }
        if (userDTO.getUserAddress() != null) {
            user.setUserAddress(userDTO.getUserAddress());
        }
        if (userDTO.getUserPhone() != null) {
            user.setUserPhone(userDTO.getUserPhone());
        }
        if (userDTO.getUserHashPass() != null) {
            user.setUserHashPass(userDTO.getUserHashPass());
        }
        if (userDTO.getUserEmail() != null) {
            user.setUserEmail(userDTO.getUserEmail());
        }
    
        // Guardar el usuario actualizado en el repositorio
        userRepository.save(user);
    }
    
    
}