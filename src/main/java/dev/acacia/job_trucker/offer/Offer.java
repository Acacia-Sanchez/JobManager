package dev.acacia.job_trucker.offer;

import java.time.LocalDate;
import dev.acacia.job_trucker.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int offCompanyId;
    private String offContactName, offContactPhone, offContactEmail, offJobAddress, offLink;
    private String offSummary, offRequirements, offQuestions, offStepComments;
    private LocalDate offDate, offStepDate;
    private boolean offFavourite;

    @Enumerated(EnumType.STRING)  // valor del enum se guarda como cadena en la base de datos
    private OffStep offStep;  // llama a la clase OffStep (necesaria por ser un ENUM)

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // userId como FK
    private User user;
    
    // CREADOS CON LOMBOK LOS CONSTRUCTORES DE OFFER + LOS GETTERS Y SETTERS DE TODOS LOS ATRIBUTOS

}
