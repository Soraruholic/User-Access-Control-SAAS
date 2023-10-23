package SAAS.UAC.UserManagement;

import SAAS.UAC.UPR.Permission;
import SAAS.UAC.UPR.Role;
import SAAS.UAC.TenantManagement.Tenant;

import java.util.HashSet;
import java.util.List;
public class TenantUser extends Tenant{
    public TenantUser(String name, String userID, HashSet<Permission> permissionList, HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool) {
        super(name, userID, null, null, null, permissionList, roleList, permissionPool, rolePool);
    }

    public HashSet<Permission> getPermissionPool() {
        return permissionPool;
    }
    public HashSet<Role> getRolePool() {
        return rolePool;
    }
    HashSet<Permission> getPermissionList() {
        return permissionList;
    }
    HashSet<Role> getRoleList() {
        return roleList;
    }
}
