package dev.acacia.job_trucker.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserEmail(String userEmail);   // comprueba si el email ya existe en la bbdd

        // Método para autenticar al usuario con email y password
    @Query("SELECT user FROM User user WHERE user.id = :id AND user.userEmail = :userEmail AND user.userHashPass = :userHashPass")
    Optional<User> loginByuserIdAnduserEmailAnduserHashPass(
            @Param("id") Long id, 
            @Param("userEmail") String userEmail, 
            @Param("userHashPass") String userHashPass
    );

   /// Optional<User> findByUserEmail(String userEmail);  // lo usa offerService para identificar el user logado

// User findByUserName(String userName); // puesto por el video de telesco
    User findByUserEmail(String email);

}
// OJO, en los nombres de los métodos en el repositorio han de llevar el mismo nombre que en las columnas de la entidad //// 