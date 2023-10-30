package SAAS.UAC.UPR;

import java.util.HashSet;

public class Role {
    protected String roleID;
    protected String roleName;
    protected HashSet<Permission> permissionList;
    public Role(String roleID, String roleName, HashSet<Permission> permissionList) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.permissionList = permissionList;
    }

    public String getRoleID() {
        return roleID;
    }
    public String getRoleName() {
        return roleName;
    }
    public HashSet<Permission> getPermissionList() {
        return permissionList;
    }
}
