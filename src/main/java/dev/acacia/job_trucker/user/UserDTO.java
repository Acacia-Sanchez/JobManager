package dev.acacia.job_trucker.user;

public class UserDTO {

    private String userName;
    private String userAddress;
    private String userPhone;
    private String userHashPass;
    private String userEmail;
    
    private UserRole userRole;

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
    
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    
}