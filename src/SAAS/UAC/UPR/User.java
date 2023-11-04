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

    public User(String name, String userID , HashSet<Permission> permissionList , HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool) {
        this.name = name;
        this.userID = userID;
        this.permissionList = permissionList;
        this.roleList = roleList;
        this.permissionPool = permissionPool;
        this.rolePool = rolePool;
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
    public String getName() {
        return name;
    }
}





