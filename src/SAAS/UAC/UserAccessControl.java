package SAAS.UAC;
import SAAS.Database.Database;
import SAAS.UAC.TenantManagement.PlatformAdministrator;
import SAAS.UAC.UPR.*;
import SAAS.Utils.GlobalVariables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserAccessControl {
    public static void add_role(User current_user, Role role) throws Exception {
        List<String> permission_IDs = new ArrayList<>();
        for (Permission permission : role.getPermissionList()){
            permission_IDs.add(permission.getPermissionID());
        }
        try {
            add_role(current_user, role.getRoleID(), role.getRoleName(), permission_IDs);
        } catch (Exception e) {
            throw e;
        }
    }
    public static void add_role(User current_user, String role_ID, String role_name, List<String> permission_IDs) throws Exception {
        // check whether The administrator has the permission to add a role, throw an exception if not
        if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_ADD_CHECK))){
            throw new IllegalArgumentException("The administrator does not have the permission to add a role");
        }

        // check whether the role_ID is null, throw an exception if it is null
        if (role_ID == null){
            throw new IllegalArgumentException("The role_ID is null");
        }

        // check whether the role name is null, throw an exception if it is null
        if (role_name == null){
            throw new IllegalArgumentException("The role name is null");
        }

        // check whether the role already exists in the role pool of his/her, throw an exception if it already exists
        if (current_user.getRolePool().contains(GlobalRole.getRoleByID(role_ID))){
            throw new IllegalArgumentException("The role already exists in the role pool of yours");
        }

        // check whether the role already exists in the global role pool, throw an exception if it already exists
        if (GlobalRole.getRoleByID(role_ID) != null){
            throw new IllegalArgumentException("The role already exists in the global role pool");
        }

        // check whether the permission_IDs is null, throw an exception if it is null
        if (permission_IDs == null){
            throw new IllegalArgumentException("No permissions will be added to the role");
        }

        // check whether the permission_IDs is empty, throw an exception if it is empty
        if (permission_IDs.isEmpty()){
            throw new IllegalArgumentException("No permissions will be added to the role");
        }

        // check whether the roel_ID is an empty string, throw an exception if it is an empty string
        if (role_ID.equals("")){
            throw new IllegalArgumentException("The role_ID is an empty string");
        }

        // check whether the role_name is an empty string, throw an exception if it is an empty string
        if (role_name.equals("")){
            throw new IllegalArgumentException("The role_name is an empty string");
        }

        // check whether the permissions in the permission_IDs exist in the permission pool of his/her, throw an exception if not
        for (String permission_ID : permission_IDs){
            if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(permission_ID))){
                throw new IllegalArgumentException("The permission: " + permission_ID +" does not exist in the permission pool of you");
            }
        }

        // pass all the checks, then add the role to the role pool of his/her

        // update the role pool of the global role pool
        HashSet<Permission> permissions = new HashSet<>();
        for (String permission_ID : permission_IDs){
            permissions.add(GlobalPermission.getPermissionByID(permission_ID));
        }
        try {
            GlobalRole.addRole(new Role(role_ID, role_name, permissions));
        } catch (Exception e) {
            throw e;
        }

        // creating a hashset to store the permissions of the role
        current_user.getRolePool().add(GlobalRole.getRoleByID(role_ID));


        // update the Role table and User table in the database
        String[] user_types = current_user.getClass().toString().split("\\.");
        switch (user_types[user_types.length - 1]){
            case "PlatformAdministrator":
                Database.update_platformAdministrator_rolePool((PlatformAdministrator) current_user);
                break;
//            case "Tenant":
//                Database.update_platformManager_rolePool(current_user);
//                break;
//            case "TenantAdministrator":
//                Database.update_platformStaff_rolePool(current_user);
//                break;
//            case "TenantUser":
//                Database.update_platformUser_rolePool(current_user);
//                break;
            default:
                throw new Exception("The user type is not supported");
        }

        // update the Role table in the database
        Database.insert_Role(new Role(role_ID, role_name, permissions), false);
    }

    public static void delete_role(User current_user, Role role) throws Exception {
        List<String> permission_IDs = new ArrayList<>();
        for (Permission permission : role.getPermissionList()){
            permission_IDs.add(permission.getPermissionID());
        }
        try {
            delete_role(current_user, role.getRoleID(), role.getRoleName(), permission_IDs);
        } catch (Exception e) {
            throw e;
        }
    }
    public static void delete_role(User current_user, String role_ID, String role_name, List<String> permission_IDs) throws Exception {
        // check whether The administrator has the permission to delete a role, throw an exception if not
        if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_DELETE_CHECK))){
            throw new IllegalArgumentException("The administrator does not have the permission to delete a role");
        }

        // check whether the role_ID is null, throw an exception if it is null
        if (role_ID == null){
            throw new IllegalArgumentException("The role_ID is null");
        }

        // check whether the role name is null, throw an exception if it is null
        if (role_name == null){
            throw new IllegalArgumentException("The role name is null");
        }

        // check whether the roel_ID is an empty string, throw an exception if it is an empty string
        if (role_ID.equals("")) {
            throw new IllegalArgumentException("The role_ID is an empty string");
        }

        // check whether the role_name is an empty string, throw an exception if it is an empty string
        if (role_name.equals("")) {
            throw new IllegalArgumentException("The role_name is an empty string");
        }

        // check whether the role already exists in the role pool of his/her, throw an exception if it not
        if (!current_user.getRolePool().contains(GlobalRole.getRoleByID(role_ID))){
            throw new IllegalArgumentException("The role does not exist in the role pool of yours");
        }

        // check whether the elements in permission_IDs is the same as the permission ID list of the role in global role pool, throw an exception if not
        List<String> globalRolePermissionIDs = new ArrayList<>();
        for (Permission permission : GlobalRole.getRoleByID(role_ID).getPermissionList()){
            globalRolePermissionIDs.add(permission.getPermissionID());
        }
        if (!permission_IDs.containsAll(globalRolePermissionIDs) || !globalRolePermissionIDs.containsAll(permission_IDs)){
            throw new IllegalArgumentException("The permission_IDs is not the same as the permission ID list of the role in global role pool");
        }

        // delete the role from the role pool of his/her
        current_user.getRolePool().remove(GlobalRole.getRoleByID(role_ID));

        // update the User table in the database
        String[] user_types = current_user.getClass().toString().split("\\.");
        switch (user_types[user_types.length - 1]) {
            case "PlatformAdministrator":
                Database.update_platformAdministrator_rolePool((PlatformAdministrator) current_user);
                break;
//            case "Tenant":
//                Database.update_platformManager_rolePool(current_user);
//                break;
//            case "TenantAdministrator":
//                Database.update_platformStaff_rolePool(current_user);
//                break;
//            case "TenantUser":
//                Database.update_platformUser_rolePool(current_user);
//                break;
            default:
                throw new Exception("The user type is not supported");
        }
    }

}
