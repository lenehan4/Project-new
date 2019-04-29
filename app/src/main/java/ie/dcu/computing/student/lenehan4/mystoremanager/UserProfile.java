package ie.dcu.computing.student.lenehan4.mystoremanager;

public class UserProfile {
    public String userStatus;
    public String userEmail;
    public String userName;
    public String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UserProfile(){

    }

    public UserProfile(String userStatus, String userEmail, String userName, String companyName) {
        this.userStatus = userStatus;
        this.userEmail = userEmail;
        this.userName = userName;
        this.companyName = companyName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
