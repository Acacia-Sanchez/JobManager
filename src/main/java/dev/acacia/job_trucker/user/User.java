package dev.acacia.job_trucker.user;

import java.util.List;

import dev.acacia.job_trucker.offer.Offer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName, userAddress, userPhone, userHashPass;
    
    @Enumerated(EnumType.STRING)  // valor del enum se guarda como cadena en la base de datos
    private UserRole userRole;  // llama a la clase UserRole (necesaria por ser un ENUM)
 
    @Column(name = "user_email", unique = true)
    private String userEmail;
    
    @OneToMany(mappedBy = "user")
    private List<Offer> offers;
 
    public User (String userName, String userAddress, String userPhone, String userHashPass, String userEmail, UserRole userRole) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userHashPass = userHashPass;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    public User () {
    
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserHashPass() {
        return userHashPass;
    }

    public void setUserHashPass(String userHashPass) {
        this.userHashPass = userHashPass;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", password='" + userHashPass + '\'' +
                ", email='" + userEmail + '\'' +
                ", role='" + userRole + '\'' +
                '}';
    }

}