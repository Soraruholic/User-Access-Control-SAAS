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
    protected HashSet<Service> serviceList;
    protected HashSet<String> tenantAdministratorList;
    protected HashSet<String> tenantUserList;
    public Tenant(String name, String userID, HashSet<Service> serviceList, HashSet<String> tenantAdministratorList, HashSet<String> tenantUserList, HashSet<Permission> permissionList, HashSet<Role> roleList, HashSet<Permission> permissionPool, HashSet<Role> rolePool) {
        super(name, userID, permissionList, roleList, permissionPool, rolePool);
        this.serviceList = serviceList;
        this.tenantAdministratorList = tenantAdministratorList;
        this.tenantUserList = tenantUserList;
    }

    public HashSet<Service> getServiceList() {
        return serviceList;
    }
    public HashSet<String> getTenantAdministratorList() {
        return tenantAdministratorList;
    }
    public HashSet<String> getTenantUserList() {
        return tenantUserList;
    }

}