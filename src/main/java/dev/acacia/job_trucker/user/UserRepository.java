package dev.acacia.job_trucker.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserEmail(String userEmail); 
    @Query("SELECT user FROM User user WHERE user.id = :id AND user.userEmail = :userEmail AND user.userHashPass = :userHashPass")
    Optional<User> loginByuserIdAnduserEmailAnduserHashPass(
            @Param("id") Long id, 
            @Param("userEmail") String userEmail, 
            @Param("userHashPass") String userHashPass
    );
    User findByUserEmail(String email);
}