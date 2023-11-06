package SAAS.UAC.login;

import SAAS.UAC.UPR.Permission;
import SAAS.UAC.UPR.Role;
import SAAS.UAC.UPR.User;

import java.util.HashSet;

public class LUser extends User {
    String email;
    String password_sha;
    String phonenumber;


    public LUser(String name, String userID, HashSet<Permission> permissionList, HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool,String email, String password_sha,String phonenumber) {
        super(name, userID, permissionList, roleList, permissionPool, rolePool);
        this.email = email;
        this.phonenumber = phonenumber;
        this.password_sha = password_sha;
    }

    public String getUsername() {
        return name;
    }
    public void setUsername(String username) {
        this.name = username;
    }
    public String getPassword() {
        return password_sha;
   }
    public void setPassword(String password) {
       this.password_sha = password;
   }

    public String getPhonenumber() {
       return phonenumber;
   }
    public String getEmail() {
        return email;
    }
}
