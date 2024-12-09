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

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String offCompanyName, offContactName, offContactPhone, offContactEmail, offJobAddress, offLink;
    private String offSummary, offRequirements, offQuestions, offStepComments;
    private LocalDate offDate, offStepDate;
    private boolean offFavourite;

    @Enumerated(EnumType.STRING)  // valor del enum se guarda como cadena en la base de datos
    private OffStep offStep;  // llama a la clase OffStep (necesaria por ser un ENUM)

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // userId como FK
    private User user;

    public Offer(String offCompanyName, String offContactName, String offContactPhone, String offContactEmail,
            String offJobAddress, String offLink, String offSummary, String offRequirements, String offQuestions,
            String offStepComments, LocalDate offDate, LocalDate offStepDate, boolean offFavourite, OffStep offStep,
            User user) {
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
        this.user = user;
    }

    public Offer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    public User getUser() {
    return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

}