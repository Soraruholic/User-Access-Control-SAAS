package SAAS.UAC.TenantManagement;

import SAAS.UAC.UPR.Permission;
import SAAS.UAC.UPR.Role;
import SAAS.UAC.UPR.Service;
import SAAS.UAC.UPR.User;
import SAAS.UAC.UserManagement.TenantAdministrator;
import SAAS.UAC.UserManagement.TenantUser;


import java.util.HashSet;
import java.util.List;

public class Tenant extends User{
    HashSet<Service> serviceList;
    HashSet<TenantAdministrator> tenantAdministratorList;
    HashSet<TenantUser> tenantUserList;
    public Tenant(String name, String userID, HashSet<Service> serviceList, HashSet<TenantAdministrator> tenantAdministratorList, HashSet<TenantUser> tenantUserList, HashSet<Permission> permissionList, HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool) {
        super(name, userID, permissionList, roleList, permissionPool, rolePool);
        this.serviceList = serviceList;
        this.tenantAdministratorList = tenantAdministratorList;
        this.tenantUserList = tenantUserList;
    }

    HashSet<Service> getServiceList() {
        return serviceList;
    }
    HashSet<TenantAdministrator> getTenantAdministratorList() {
        return tenantAdministratorList;
    }
    HashSet<TenantUser> getTenantUserList() {
        return tenantUserList;
    }
    HashSet<Permission> getPermissionPool() {
        return permissionPool;
    }
    HashSet<Role> getRolePool() {
        return rolePool;
    }
    HashSet<Permission> getPermissionList() {
        return permissionList;
    }
    HashSet<Role> getRoleList() {
        return roleList;
    }
}