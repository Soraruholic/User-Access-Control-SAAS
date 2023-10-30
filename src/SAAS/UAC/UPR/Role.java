package SAAS.UAC.UPR;
public class Role {
    protected String roleID;
    protected String roleName;
    protected PermissionLevel permissionLevel;
    public Role(String roleID, String roleName, PermissionLevel permissionLevel) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.permissionLevel = permissionLevel;
    }

    public String getRoleID() {
        return roleID;
    }
    public String getRoleName() {
        return roleName;
    }
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }
}
