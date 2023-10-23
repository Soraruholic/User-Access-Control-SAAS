package SAAS.UAC.UPR;
public abstract class Permission {
    protected String permissionID;
    protected String permissionName;
    public Permission(String permissionID, String permissionName) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
    }
}
