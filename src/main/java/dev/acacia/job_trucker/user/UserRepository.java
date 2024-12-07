package dev.acacia.job_trucker.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserEmail(String userEmail);   // comprueba si el email ya existe en la bbdd
    
}
