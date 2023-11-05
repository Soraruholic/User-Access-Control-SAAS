package SAAS.UAC.UPR;
public abstract class Permission {
    protected String permissionID;
    protected String permissionName;
    public Permission(String permissionID, String permissionName) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
    }
//     @Override
//     public boolean equals(Object obj) {
//        if (obj instanceof Permission) {
//                 Permission permission = (Permission) obj;
//                return permissionID.equalsIgnoreCase(permission.getPermissionID().trim());
//             }
//        return false;
//    }
    public String getPermissionID() {
        return permissionID;
    }
    public String getPermissionName() {
        return permissionName;
    }
}
