package dev.acacia.job_trucker.user;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

            // Comprobar si email ya está registrado en la bbdd, antes de guardar el DTO en User
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new EmailAlreadyExistsException(
            "\n   ERROR: The email address is already in use. Please choose another one.");
        }

        UserRole role = userDTO.getUserRole();
        String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());  // Encripta el password
        
        if (role == null) {
            role = UserRole.USER;
        }

            // Si el admin está registrando un nuevo usuario y se especifica un rol
        if (userDTO.getUserRole() != null && userDTO.getUserRole() == UserRole.ADMIN) { // Comparar con el enum directamente

                // Verificar que el usuario autenticado tiene permisos de ADMIN para asignar el rol ADMIN
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AccessDeniedException("YOUR ROLE IS NOT ADMIN. Only an ADMIN can assign the ADMIN role.");
            }
            role = UserRole.ADMIN;  // Asignamos el rol ADMIN si el usuario es ADMIN
        }

        User user = new User(
                userDTO.getUserName(),
                userDTO.getUserAddress(),
                userDTO.getUserPhone(),
                encodedPassword,
                userDTO.getUserEmail(),
                role);

        userRepository.save(user);  // Guarda el DTO en User
        return user;  // muestra el User
    }

    public User registerUserWithRole(UserDTO userDTO) {  // solo para ADMINs

        // Comprobar si email ya está registrado en la bbdd, antes de guardar el DTO en User
        if (userRepository.existsByUserEmail(userDTO.getUserEmail())) {
            throw new EmailAlreadyExistsException(
            "\n   ERROR: The email address is already in use. Please choose another one.");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getUserHashPass());  // Encripta el password

        UserRole role = UserRole.USER;  // Asignamos el rol USER por defecto    

        // Si userRole no es null, lo asignamos directamente
        if (userDTO.getUserRole() != null) {
            role = userDTO.getUserRole();  // Asignamos el valor recibido directamente
        }

        User user = new User(
                userDTO.getUserName(),
                userDTO.getUserAddress(),
                userDTO.getUserPhone(),
                encodedPassword,
                userDTO.getUserEmail(),
                role);  // asigna el rol proporcionado

        userRepository.save(user);  // Guarda el DTO en User
        return user;  // muestra el User
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
        // ya que userDTO es lo que se envía desde postman
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
                        "\n   ERROR: The email address is already in use. Please choose another one.");
            }
            user.setUserEmail(userDTO.getUserEmail());
        }


        // PARA MODIFICAR EL ROL /////
        
            // Verificar si el usuario autenticado tiene permisos para modificar roles
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("YOUR ROLE IS NOT ADMIN. You are not allowed to modify another user's role.");
        }

        // Si el admin intenta modificar el rol, hacerlo solo si el role no es null
        if (userDTO.getUserRole() != null) {
            UserRole newRole = userDTO.getUserRole();   
            user.setUserRole(newRole); // cambia el rol del usuario
        }

        // Guardar el usuario actualizado en el repositorio
        userRepository.save(user);
        return user; // Devolver el usuario actualizado en la respuesta
    }


    public boolean login(Long id, String userEmail, String userHashPass) {
        if (id == null || !userRepository.existsById(id)) {
            throw new GlobalExceptionHandler.UserNotFoundException();
        }
        
        // le paso userEmail y userHashPass al metodo del repository y lo que devuelve el método se guarda en el objeto user
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