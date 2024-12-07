package dev.acacia.job_trucker.user;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.EmailAlreadyExistsException;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.NoUsersFoundException;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);  // muestra logs de errores de UserService

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO) {
        // Comprobar si email ya está registrado en la bbdd, antes de guardar el DTO en
        // User
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new EmailAlreadyExistsException(
                    "ERROR: The email address is already in use. Please choose another one.");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());
        User user = new User(
                userDTO.getUserName(),
                userDTO.getUserAddress(),
                userDTO.getUserPhone(),
                encodedPassword,
                userDTO.getUserEmail());
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

    public User updateUser(Long id, UserDTO userDTO) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }

        // Obtener el usuario existente
        User user = getUser(id);

        // Actualizar campos solo si no son nulos en postman
        // ya que userDTO es lo que se envía dede postman
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
            String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());
            user.setUserHashPass(encodedPassword); // guarda la pass encriptada, no la pasada por DTO
        }
        if (userDTO.getUserEmail() != null) {
            // Comprobar si email ya está registrado en la bbdd, antes de guardar el DTO en
            // User
            if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
                throw new EmailAlreadyExistsException(
                        "ERROR: The email address is already in use. Please choose another one.");
            }
            user.setUserEmail(userDTO.getUserEmail());
        }

        // Guardar el usuario actualizado en el repositorio
        userRepository.save(user);
        return user;
    }

    public boolean login(Long id, String userEmail, String userHashPass) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        
        // le paso userEmail y userHashPass al metodo del repository y lo que devuelve el método se guarda en el objeto user
        Optional<User> user = userRepository.loginByuserIdAnduserEmailAnduserHashPass(id, userEmail, userHashPass);
        if (user.isPresent()) {  
            logger.info("Login successful for user: {}", userEmail);
            return true;
        }
    
        logger.warn("Login failed for user: {}", userEmail);
        return false;
    }
    

    public void logout(Long id, String userEmail) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
    }

}