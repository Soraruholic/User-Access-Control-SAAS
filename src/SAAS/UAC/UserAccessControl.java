package SAAS.UAC;
import SAAS.Database.Database;
import SAAS.UAC.TenantManagement.PlatformAdministrator;
import SAAS.UAC.TenantManagement.Tenant;
import SAAS.UAC.UPR.*;
import SAAS.Utils.GlobalVariables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserAccessControl {
    protected static User current_user;
    public static void init(String userID) throws Exception {
        // check whether userID is null, throw an exception if it is null
        if (userID == null){
            throw new IllegalArgumentException("userID is null");
        }

        // check whether the user exists, throw an exception if it does not exist
        if (Database.select_userBasic(userID) == null){
            throw new IllegalArgumentException("User does not exist");
        }
        
        // initialize the user
        current_user = Database.select_user_byID(userID);

        // check whether the user is null, throw an exception if it is null
        if (current_user == null){
            throw new IllegalArgumentException("User does not exist");
        }
    }
    public static boolean check_permission(User user, String functionID) throws Exception {
        // check whether functionID is null, throw an exception if it is null
        if (functionID == null){
            throw new IllegalArgumentException("functionID is null");
        }

        // check whether the user has the permission whose functionList contains the functionID, throw an exception if it does not have the permission
        boolean has_permission = false;
        for (Permission permission : user.getPermissionList()){
            // if the type of permission is FunctionalPermission
            if (permission instanceof FunctionalPermission){
                if (((FunctionalPermission) permission).getFunctionIDList().contains(functionID)){
                    has_permission = true;
                    break;
                }
            }
        }
        return has_permission;
    }
    public static void add_role(User current_user, Role role) throws Exception {
        // check whether the role is null, throw an exception if it is null
        if (role == null){
            throw new IllegalArgumentException("The role is null");
        }
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
        if (GlobalRole.getRoleByID(role_ID) != null && current_user.getRolePool().contains(GlobalRole.getRoleByID(role_ID))){
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
        // check whether the role is null, throw an exception if it is null
        if (role == null){
            throw new IllegalArgumentException("The role is null");
        }
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


    public static void assign_role(User current_user, User assigned_user, Role role) throws Exception {
        if (role == null){
            throw new IllegalArgumentException("The role is null");
        }
        List<String> permission_IDs = new ArrayList<>();
        for (Permission permission : role.getPermissionList()){
            permission_IDs.add(permission.getPermissionID());
        }
        try {
            assign_role(current_user, assigned_user, role.getRoleID(), role.getRoleName(), permission_IDs);
        } catch (Exception e) {
            throw e;
        }
    }
    public static void assign_role(User current_user, User assigned_user, String role_ID, String role_name, List<String> permission_IDs) throws Exception {
        // do some check
        // 0. The administrator has the permission to assign a role, throw an exception if not
        if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_ASSIGN_CHECK))){
            throw new IllegalArgumentException("The administrator does not have the permission to assign a role to a tenant");
        }

        // 4. Do some basic check
        // 4.5 check whether the role_ID is null, throw an exception if it is null
        if (role_ID == null){
            throw new IllegalArgumentException("The role_ID is null");
        }

        // 4.6 check whether the role_name is null, throw an exception if it is null
        if (role_name == null){
            throw new IllegalArgumentException("The role_name is null");
        }

        // 4.7 check whether the permission_IDs is null, throw an exception if it is null
        if (permission_IDs == null){
            throw new IllegalArgumentException("The permission_IDs is null");
        }
        // 4.1 whether the role_ID is empty, throw an exception if so
        if (role_ID.equals("")){
            throw new IllegalArgumentException("The role_ID is empty");
        }

        // 4.2 whether the role_name is empty, throw an exception if so
        if (role_name.equals("")){
            throw new IllegalArgumentException("The role_name is empty");
        }

        // 4.3 whether the permission_IDs is empty, throw an exception if so
        if (permission_IDs.size() == 0){
            throw new IllegalArgumentException("The permission_IDs is empty");
        }

        // 4.4 check whether the assigned_user is null, throw an exception if it is null
        if (assigned_user == null){
            throw new IllegalArgumentException("The assigned_user is null");
        }

        // 0. The administrator has the permission to assign a role to the specific user, throw an exception if not
        String[] assigned_user_types = assigned_user.getClass().toString().split("\\.");
        // String[] current_user_types = current_user.getClass().toString().split("\\.");
        // System.out.println(assigned_user_types[assigned_user_types.length - 1]);
        // System.out.println(current_user_types[current_user_types.length - 1]);
        switch (assigned_user_types[assigned_user_types.length - 1]) {
            case "PlatformAdministrator" -> {
                if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_ASSIGN2PLATFORM_ADMINISTRATOR))) {
                    throw new IllegalArgumentException("The administrator does not have the permission to assign a role to a platform administrator");
                }
            }
            case "Tenant" -> {
                if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_ASSIGN2TENANT))) {
                    throw new IllegalArgumentException("The administrator does not have the permission to assign a role to a tenant");
                }
            }
            case "TenantAdministrator" -> {
                if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_ASSIGN2TENANT_ADMINISTRATOR))) {
                    throw new IllegalArgumentException("The administrator does not have the permission to assign a role to a tenant administrator");
                }
            }
            case "TenantUser" -> {
                if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(GlobalVariables.ROLE_ASSIGN2TENANT_USER))) {
                    throw new IllegalArgumentException("The administrator does not have the permission to assign a role to a tenant user");
                }
            }
            default -> throw new Exception("The user type is not supported");
        }

        // 2. Whether the role exists in the (Global)rolePool, throw an exception if not
        if (GlobalRole.getRoleByID(role_ID) == null){
            throw new IllegalArgumentException("The role does not exist in the Global rolePool");
        }
        if (!current_user.getRoleList().contains(GlobalRole.getRoleByID(role_ID))){
            throw new IllegalArgumentException("The role does not exists in the role pool of yours");
        }

        // 3. Whether the role already exists in the roleList of the tenant, throw an exception if so
        if (assigned_user.getRoleList().contains(GlobalRole.getRoleByID(role_ID))){
            throw new IllegalArgumentException("The role already exists in the roleList of the assigned user");
        }


        // pass all the checks, then add the role to the role pool of the assigned user's roleList
        assigned_user.getRoleList().add(GlobalRole.getRoleByID(role_ID));
        // update the User table in the database
        switch (assigned_user_types[assigned_user_types.length - 1]) {
//            case "PlatformAdministrator":
//                Database.update_platformAdministrator_roleList((PlatformAdministrator) assigned_user);
//                break;
            case "Tenant":
                Database.update_tenant_roleList((Tenant) assigned_user);
                assigned_user.getRoleList().add(GlobalRole.getRoleByID(role_ID));
                break;
//            case "TenantAdministrator":
//                Database.update_platformStaff_rolePool(assigned_user);
//                break;
//            case "TenantUser":
//                Database.update_platformUser_rolePool(assigned_user);
//                break;
            default:
                throw new Exception("The user type is not supported");
        }
    }

    public static void deprive_role(User current_user, User deprived_user, Role role) throws Exception {
        if (role == null){
            throw new IllegalArgumentException("The role is null");
        }
        List<String> permission_IDs = new ArrayList<>();
        for (Permission permission : role.getPermissionList()){
            permission_IDs.add(permission.getPermissionID());
        }
        try {
            assign_role(current_user, deprived_user, role.getRoleID(), role.getRoleName(), permission_IDs);
        } catch (Exception e) {
            throw e;
        }
    }
    public static void deprive_role(User current_user, User assigned_user, String role_ID, String role_name, List<String> permission_IDs) throws Exception {
        // 1. Do some basic check
        // 1.1 check whether the current_user is null, throw an exception if it is null
        if (current_user == null){
            throw new IllegalArgumentException("The current_user is null");
        }

        // 1.2 check whether the assigned_user is null, throw an exception if it is null
        if (assigned_user == null){
            throw new IllegalArgumentException("The assigned_user is null");
        }

        // 1.3 check whether the role_ID is null, throw an exception if it is null
        if (role_ID == null){
            throw new IllegalArgumentException("The role_ID is null");
        }

        // 1.4 check whether the role_name is null, throw an exception if it is null
        if (role_name == null){
            throw new IllegalArgumentException("The role_name is null");
        }

        // 1.5 check whether the permission_IDs is null, throw an exception if it is null
        if (permission_IDs == null){
            throw new IllegalArgumentException("The permission_IDs is null");
        }

        // 1.6 check whether the role_ID is empty, throw an exception if so
        if (role_ID.equals("")){
            throw new IllegalArgumentException("The role_ID is empty");
        }

        // 1.7 check whether the role_name is empty, throw an exception if so
        if (role_name.equals("")){
            throw new IllegalArgumentException("The role_name is empty");
        }

        // 1.8 check whether the permission_IDs is empty, throw an exception if so
        if (permission_IDs.size() == 0){
            throw new IllegalArgumentException("The permission_IDs is empty");
        }

    }

    public static void assign_permission(User current_user, User assigned_user, Permission permission) throws Exception {

    }

    public static void deprive_permission(User current_user, User deprived_user, Permission permission) throws Exception {

    }
}
