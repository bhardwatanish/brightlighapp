package group_one.brightlights.Model;

public class User {
    private String userNameR;
    private String passwordR;


    public User(){

    }

    public User(String userNameR, String passwordR){
        this.userNameR =userNameR;
        this.passwordR=passwordR;

    }

    public String getPasswordR() {
        return passwordR;
    }

    public String getUserNameR() {
        return userNameR;
    }

    public void setPasswordR(String passwordR) {
        this.passwordR = passwordR;
    }

    public void setUserNameR(String userNameR) {
        this.userNameR = userNameR;
    }
}

