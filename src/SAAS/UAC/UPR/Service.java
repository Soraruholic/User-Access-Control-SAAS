package SAAS.UAC.UPR;

import java.util.HashSet;

public class Service {
    protected String serviceID;
    protected String serviceName;
    protected HashSet<Permission> permissionList;
    protected HashSet<Role> roleList;
    protected String price;
    public Service(String serviceID, String serviceName, HashSet<Permission> permissionList, HashSet<Role> roleList, String price) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.permissionList = permissionList;
        this.roleList = roleList;
        this.price = price;
    }
    public String getServiceID() {
        return serviceID;
    }
    public String getServiceName() {
        return serviceName;
    }
    public HashSet<Permission> getPermissionList() {
        return permissionList;
    }
    public HashSet<Role> getRoleList() {
        return roleList;
    }
    public String getPrice() {
        return price;
    }

}
