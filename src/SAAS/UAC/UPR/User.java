package SAAS.UAC.UPR;

import java.util.HashSet;

// User is an abstract class that represents a user in the system.
public abstract class User {
    protected String name;
    protected String userID;
    protected HashSet<Permission> permissionList;
    protected HashSet<Role> roleList;
    protected HashSet<Permission> permissionPool;
    protected HashSet<Role> rolePool;

    protected String passwordMd5;

    protected String phonenumber;


    public User(String name, String userID , HashSet<Permission> permissionList , HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool) {
        this.name = name;
        this.userID = userID;
        this.permissionList = permissionList;
        this.roleList = roleList;
        this.permissionPool = permissionPool;
        this.rolePool = rolePool;
        // this.phonenumber = phonenumber;
        // this.passwordMd5 = passwordMd5;
    }


    public String getName() {
        return name;
    }
    public String getUserID() {
        return userID;
    }
    public HashSet<Permission> getPermissionList() {
        return permissionList;
    }
    public HashSet<Role> getRoleList() {
        return roleList;
    }
    public HashSet<Permission> getPermissionPool() {
        return permissionPool;
    }
    public HashSet<Role> getRolePool() {
        return rolePool;
    }

//    public String getUsername() {
//        return name;
//    }
//    public void setUsername(String username) {
//        this.name = username;
//    }
//    public String getPassword() {
//        return passwordMd5;
//    }
//    public void setPassword(String password) {
//        this.passwordMd5 = password;
//    }
//    public void setPersonID(String personID) {
//        this.personID = personID;
//    }
//    public String getPhonenumber() {
//        return phonenumber;
//    }
//    public void setPhonenumber(String phonenumber) {
//        this.phonenumber = phonenumber;
//    }
}





