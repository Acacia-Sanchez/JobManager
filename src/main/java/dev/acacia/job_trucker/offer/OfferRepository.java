package dev.acacia.job_trucker.offer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE o.user.id = :userId")
    List<Offer> findByUser(@Param("userId") long userId);

    boolean existsByUserId(Long id);
}