package dev.acacia.job_trucker.user;

import java.util.List;

import dev.acacia.job_trucker.offer.Offer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    
    @Column(unique = true)
    private String userEmail;
    
    @OneToMany(mappedBy = "user")
    private List<Offer> offer;
 
    public User (String userName, String userAddress, String userPhone, String userHashPass, String userEmail) {
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userHashPass = userHashPass;
        this.userEmail = userEmail;
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
        return offer;
    }

    public void setOffers(List<Offer> offer) {
        this.offer = offer;
    }

}
