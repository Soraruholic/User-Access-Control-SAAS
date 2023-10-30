package SAAS.UAC.UserManagement;

import SAAS.UAC.TenantManagement.Tenant;
import SAAS.UAC.UPR.Permission;
import SAAS.UAC.UPR.Role;
import SAAS.UAC.UPR.Service;
import SAAS.UAC.UPR.User;

import java.util.HashSet;
import java.util.List;
public class TenantAdministrator extends Tenant {
    public TenantAdministrator(String name, String userID, HashSet<Permission> permissionList, HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool) {
        super(name, userID, null, null, null, permissionList, roleList, permissionPool, rolePool);
    }

    // Operations for Permission
    public void addPermission(Permission permission){
        // first check whether the permission already exists in the permissionPool, throw an exception if it already exists
        if (permissionPool.contains(permission)){
            throw new IllegalArgumentException("Permission already exists");
        }

        // then add the permission to the permissionPool
        permissionPool.add(permission);
    }
    public void deletePermission(Permission permission){
        // first check whether the permission exists in the permissionPool, throw an exception if it does not exist
        if (!permissionPool.contains(permission)){
            throw new IllegalArgumentException("Permission does not exist");
        }

        // then delete the permission from the permissionPool
        permissionPool.remove(permission);
    }
    public void assignPermissionTo(Permission permission, TenantUser tenantUser) {
        // first check whether the permission exists in the permissionPool, throw an exception if it does not exist
        if (!permissionPool.contains(permission)){
            throw new IllegalArgumentException("Permission does not exist");
        }

        // then check whether the tenant already has the permission, throw an exception if it already has the permission
        if (tenantUser.getPermissionList().contains(permission)){
            throw new IllegalArgumentException("Tenant already has the permission");
        }

        // then add the permission to the tenant's permissionList
        tenantUser.getPermissionList().add(permission);
    }
    public void deprivePermissionFrom(Permission permission, TenantUser tenantUser) {
        // first check whether the permission exists in the permissionPool, throw an exception if it does not exist
        if (!permissionPool.contains(permission)){
            throw new IllegalArgumentException("Permission does not exist");
        }

        // then check whether the tenant has the permission, throw an exception if it does not have the permission
        if (!tenantUser.getPermissionList().contains(permission)){
            throw new IllegalArgumentException("Tenant does not have the permission");
        }

        // then delete the permission from the tenant's permissionList
        tenantUser.getPermissionList().remove(permission);
    }

    // Operations for Role
    public void addRole(Role role){
        // first check whether the role already exists in the rolePool, throw an exception if it already exists
        if (rolePool.contains(role)){
            throw new IllegalArgumentException("Role already exists");
        }

        // then add the role to the rolePool
        rolePool.add(role);
    }
    public void deleteRole(Role role){
        // first check whether the role exists in the rolePool, throw an exception if it does not exist
        if (!rolePool.contains(role)){
            throw new IllegalArgumentException("Role does not exist");
        }

        // then delete the role from the rolePool
        rolePool.remove(role);
    }
    public void assignRoleTo(Role role, TenantUser tenantUser) {
        // first check whether the role exists in the rolePool, throw an exception if it does not exist
        if (!rolePool.contains(role)){
            throw new IllegalArgumentException("Role does not exist");
        }

        // then check whether the tenant already has the role, throw an exception if it already has the role
        if (tenantUser.getRoleList().contains(role)){
            throw new IllegalArgumentException("Tenant already has the role");
        }

        // then add the role to the tenant's roleList
        tenantUser.getRoleList().add(role);
    }
    public void depriveRoleFrom(Role role, TenantUser tenantUser) {
        // first check whether the role exists in the rolePool, throw an exception if it does not exist
        if (!rolePool.contains(role)){
            throw new IllegalArgumentException("Role does not exist");
        }

        // then check whether the tenant has the role, throw an exception if it does not have the role
        if (!tenantUser.getRoleList().contains(role)){
            throw new IllegalArgumentException("Tenant does not have the role");
        }

        // then delete the role from the tenant's roleList
        tenantUser.getRoleList().remove(role);
    }






}
