package dev.acacia.job_trucker.offer;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {

    private String offCompanyName, offContactName, offContactPhone, offContactEmail, offJobAddress, offLink;
    private String offSummary, offRequirements, offQuestions, offStepComments;
    private LocalDate offDate, offStepDate;
    private boolean offFavourite;

    @Enumerated(EnumType.STRING)  // valor del enum se guarda como cadena en la base de datos
    private OffStep offStep;  // llama a la clase OffStep (necesaria por ser un ENUM)


    // AQUÍ DEBAJO IRÍAN LOS GETTERS Y SETTERS DE TODOS LOS ATRIBUTOS
    // PERO TENGO LOMBOK, ASÍ QUE...
}