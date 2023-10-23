package SAAS.UAC.TenantManagement;

import SAAS.UAC.UPR.Permission;
import SAAS.UAC.UPR.Role;
import SAAS.UAC.UPR.User;
import SAAS.UAC.UPR.Service;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;



//PlatformAdministrator inherits from User
public class PlatformAdministrator extends User{
    protected HashSet<Service> servicePool;
    public PlatformAdministrator(String name, String userID, HashSet<Permission> permissionPool, HashSet<Role> rolePool, HashSet<Service> servicePool, HashSet<Permission> permissionList, HashSet<Role> roleList) {
        super(name, userID, permissionList, roleList, permissionPool, rolePool);
        this.servicePool = servicePool;
    }
    public HashSet<Permission> getPermissionPool() {
        return permissionPool;
    }
    public HashSet<Role> getRolePool() {
        return rolePool;
    }
    public HashSet<Service> getServicePool() {
        return servicePool;
    }
    public HashSet<Permission> getPermissionList() {
        return permissionList;
    }
    public HashSet<Role> getRoleList() {
        return roleList;
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
    public void assignPermissionTo(Permission permission, Tenant tenant) {
        // first check whether the permission exists in the permissionPool, throw an exception if it does not exist
        if (!permissionPool.contains(permission)){
            throw new IllegalArgumentException("Permission does not exist");
        }

        // then check whether the tenant already has the permission, throw an exception if it already has the permission
        if (tenant.getPermissionList().contains(permission)){
            throw new IllegalArgumentException("Tenant already has the permission");
        }

        // then add the permission to the tenant's permissionList
        tenant.getPermissionList().add(permission);
    }
    public void deprivePermissionFrom(Permission permission, Tenant tenant) {
        // first check whether the permission exists in the permissionPool, throw an exception if it does not exist
        if (!permissionPool.contains(permission)){
            throw new IllegalArgumentException("Permission does not exist");
        }

        // then check whether the tenant has the permission, throw an exception if it does not have the permission
        if (!tenant.getPermissionList().contains(permission)){
            throw new IllegalArgumentException("Tenant does not have the permission");
        }

        // then delete the permission from the tenant's permissionList
        tenant.getPermissionList().remove(permission);
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
    public void assignRoleTo(Role role, Tenant tenant) {
        // first check whether the role exists in the rolePool, throw an exception if it does not exist
        if (!rolePool.contains(role)){
            throw new IllegalArgumentException("Role does not exist");
        }

        // then check whether the tenant already has the role, throw an exception if it already has the role
        if (tenant.getRoleList().contains(role)){
            throw new IllegalArgumentException("Tenant already has the role");
        }

        // then add the role to the tenant's roleList
        tenant.getRoleList().add(role);
    }
    public void depriveRoleFrom(Role role, Tenant tenant) {
        // first check whether the role exists in the rolePool, throw an exception if it does not exist
        if (!rolePool.contains(role)){
            throw new IllegalArgumentException("Role does not exist");
        }

        // then check whether the tenant has the role, throw an exception if it does not have the role
        if (!tenant.getRoleList().contains(role)){
            throw new IllegalArgumentException("Tenant does not have the role");
        }

        // then delete the role from the tenant's roleList
        tenant.getRoleList().remove(role);
    }

    // Operations for Service
    public void addService(Service service){
        // first check whether the service already exists in the servicePool, throw an exception if it already exists
        if (servicePool.contains(service)){
            throw new IllegalArgumentException("Service already exists");
        }

        // then add the service to the servicePool
        servicePool.add(service);
    }
    public void deleteService(Service service){
        // first check whether the service exists in the servicePool, throw an exception if it does not exist
        if (!servicePool.contains(service)){
            throw new IllegalArgumentException("Service does not exist");
        }

        // then delete the service from the servicePool
        servicePool.remove(service);
    }
    public void assignServiceTo(Service service, Tenant tenant) {
        // first check whether the service exists in the servicePool, throw an exception if it does not exist
        if (!servicePool.contains(service)){
            throw new IllegalArgumentException("Service does not exist");
        }

        // then check whether the tenant already has the service, throw an exception if it already has the service
        if (tenant.serviceList.contains(service)){
            throw new IllegalArgumentException("Tenant already has the service");
        }

        // then add the service to the tenant's serviceList
        tenant.serviceList.add(service);
    }
    public void depriveServiceFrom(Service service, Tenant tenant) {
        // first check whether the service exists in the servicePool, throw an exception if it does not exist
        if (!servicePool.contains(service)){
            throw new IllegalArgumentException("Service does not exist");
        }

        // then check whether the tenant has the service, throw an exception if it does not have the service
        if (!tenant.serviceList.contains(service)){
            throw new IllegalArgumentException("Tenant does not have the service");
        }

        // then delete the service from the tenant's serviceList
        tenant.serviceList.remove(service);
    }
}
