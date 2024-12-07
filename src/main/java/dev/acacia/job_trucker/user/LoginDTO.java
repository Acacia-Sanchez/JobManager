package dev.acacia.job_trucker.user;

public class LoginDTO {

    private String userEmail;
    private String userHashPass;

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserHashPass() {
        return userHashPass;
    }
    public void setUserHashPass(String userHashPass) {
        this.userHashPass = userHashPass;
    }
}