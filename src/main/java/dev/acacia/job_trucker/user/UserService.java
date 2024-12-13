package dev.acacia.job_trucker.user;

import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.EmailAlreadyExistsException;
import dev.acacia.job_trucker.exceptions.GlobalExceptionHandler.NoUsersFoundException;
import dev.acacia.job_trucker.offer.OfferRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OfferRepository offerRepository;

    public UserService(UserRepository userRepository, OfferRepository offerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO) {
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new EmailAlreadyExistsException(
            "\n   ERROR: The email address is already in use. Please choose another one.");
        }
        UserRole role = userDTO.getUserRole();
        String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());  
        if (role == null) {
            role = UserRole.USER;
        }
        if (userDTO.getUserRole() != null && userDTO.getUserRole() == UserRole.ADMIN) { 
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AccessDeniedException("YOUR ROLE IS NOT ADMIN. Only an ADMIN can assign the ADMIN role.");
            }
            role = UserRole.ADMIN; 
        }
        User user = new User(
                userDTO.getUserName(),
                userDTO.getUserAddress(),
                userDTO.getUserPhone(),
                encodedPassword,
                userDTO.getUserEmail(),
                role);

        userRepository.save(user);  
        return user;  
    }

    public User registerUserWithRole(UserDTO userDTO) { 
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new EmailAlreadyExistsException(
            "\n   ERROR: The email address is already in use. Please choose another one.");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());  
        UserRole role = UserRole.USER;      
        if (userDTO.getUserRole() != null) {
            role = userDTO.getUserRole();  
        }

        User user = new User(
                userDTO.getUserName(),
                userDTO.getUserAddress(),
                userDTO.getUserPhone(),
                encodedPassword,
                userDTO.getUserEmail(),
                role);  

        userRepository.save(user);  
        return user;  
    }

    public void deleteUser(Long id) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        // Comprobamos si el usuario tiene ofertas asociadas
        if (offerRepository.existsByUserId(id)) {
            throw new GlobalExceptionHandler.OfferAssociatedWithUserException();
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
        User user = getUser(id);
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
            if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
                throw new EmailAlreadyExistsException(
                        "\n   ERROR: The email address is already in use. Please choose another one.");
            }
            user.setUserEmail(userDTO.getUserEmail());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("You are not authenticated. Please log in.");
        }
        if (userDTO.getUserRole() != null) {
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            if (!isAdmin) {
                throw new AccessDeniedException("Only an admin can modify the user's role.");
            }
            UserRole newRole = userDTO.getUserRole();
            user.setUserRole(newRole);
        }
        userRepository.save(user);
        return user; 
    }

    public boolean login(Long id, String userEmail, String userHashPass) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        Optional<User> user = userRepository.loginByuserIdAnduserEmailAnduserHashPass(id, userEmail, userHashPass);
        if (user.isPresent()) {  
            return true;
        }
        return false;
    }
    public void logout(Long id, String userEmail) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
    }
}