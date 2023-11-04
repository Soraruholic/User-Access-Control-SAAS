package SAAS.UAC.LogIn;
import SAAS.UAC.UPR.User;
public class LogUser extends User{
    protected String password_sha;
    protected String email;
    protected String phone_number;
    protected String lastPSWChange;

    public LogUser(String name, String userID , String password_sha, String email, String phone_number, String lastPSWChange) {
        super(name, userID, null, null, null, null);
        this.password_sha = password_sha;
        this.email = email;
        this.phone_number = phone_number;
        this.lastPSWChange = lastPSWChange;
    }

    public String getPassword() {
        return password_sha;
    }
    public String getPhoneNumber() {
        return phone_number;
    }
    public void setPassword(String password) {
        this.password_sha = password;
    }
    public String getEmail() {
        return email;
    }
}
