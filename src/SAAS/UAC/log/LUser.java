package SAAS.UAC.log;

import SAAS.UAC.UPR.User;

public class LUser extends User {
    String password;
    String email;
    String password_sha;
    String phoneNumber;

    public LUser(String name, String userID, String password, String email, String password_sha) {
        super(name, userID, null, null, null, null);
        this.password = password;
        this.email = email;
        this.password_sha = password_sha;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword_sha() {
        return password_sha;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return name;
    }

}
