package dev.acacia.job_trucker.offer;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OfferDTO {

    private String offCompanyName, offContactName, offContactPhone, offContactEmail, offJobAddress, offLink;
    private String offSummary, offRequirements, offQuestions, offStepComments;
    private LocalDate offDate, offStepDate;
    private boolean offFavourite;

    @Enumerated(EnumType.STRING)  // valor del enum se guarda como cadena en la base de datos
    private OffStep offStep;  // llama a la clase OffStep (necesaria por ser un ENUM)

    private long userId;  // la FK de la tabla users

    public OfferDTO(String offCompanyName, String offContactName, String offContactPhone, String offContactEmail,
            String offJobAddress, String offLink, String offSummary, String offRequirements, String offQuestions,
            String offStepComments, LocalDate offDate, LocalDate offStepDate, boolean offFavourite, OffStep offStep,
            long userId) {
        this.offCompanyName = offCompanyName;
        this.offContactName = offContactName;
        this.offContactPhone = offContactPhone;
        this.offContactEmail = offContactEmail;
        this.offJobAddress = offJobAddress;
        this.offLink = offLink;
        this.offSummary = offSummary;
        this.offRequirements = offRequirements;
        this.offQuestions = offQuestions;
        this.offStepComments = offStepComments;
        this.offDate = offDate;
        this.offStepDate = offStepDate;
        this.offFavourite = offFavourite;
        this.offStep = offStep;
        this.userId = userId;
    }

    public OfferDTO() {
    }

    public String getOffCompanyName() {
        return offCompanyName;
    }

    public void setOffCompanyName(String offCompanyName) {
        this.offCompanyName = offCompanyName;
    }

    public String getOffContactName() {
        return offContactName;
    }

    public void setOffContactName(String offContactName) {
        this.offContactName = offContactName;
    }

    public String getOffContactPhone() {
        return offContactPhone;
    }

    public void setOffContactPhone(String offContactPhone) {
        this.offContactPhone = offContactPhone;
    }

    public String getOffContactEmail() {
        return offContactEmail;
    }

    public void setOffContactEmail(String offContactEmail) {
        this.offContactEmail = offContactEmail;
    }

    public String getOffJobAddress() {
        return offJobAddress;
    }

    public void setOffJobAddress(String offJobAddress) {
        this.offJobAddress = offJobAddress;
    }

    public String getOffLink() {
        return offLink;
    }

    public void setOffLink(String offLink) {
        this.offLink = offLink;
    }

    public String getOffSummary() {
        return offSummary;
    }

    public void setOffSummary(String offSummary) {
        this.offSummary = offSummary;
    }

    public String getOffRequirements() {
        return offRequirements;
    }

    public void setOffRequirements(String offRequirements) {
        this.offRequirements = offRequirements;
    }

    public String getOffQuestions() {
        return offQuestions;
    }

    public void setOffQuestions(String offQuestions) {
        this.offQuestions = offQuestions;
    }

    public String getOffStepComments() {
        return offStepComments;
    }

    public void setOffStepComments(String offStepComments) {
        this.offStepComments = offStepComments;
    }

    public LocalDate getOffDate() {
        return offDate;
    }

    public void setOffDate(LocalDate offDate) {
        this.offDate = offDate;
    }

    public LocalDate getOffStepDate() {
        return offStepDate;
    }

    public void setOffStepDate(LocalDate offStepDate) {
        this.offStepDate = offStepDate;
    }

    public boolean isOffFavourite() {
        return offFavourite;
    }

    public void setOffFavourite(boolean offFavourite) {
        this.offFavourite = offFavourite;
    }

    public OffStep getOffStep() {
        return offStep;
    }

    public void setOffStep(OffStep offStep) {
        this.offStep = offStep;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


}