package SAAS.UAC;
import SAAS.Database.Database;
import SAAS.UAC.TenantManagement.PlatformAdministrator;
import SAAS.UAC.TenantManagement.Tenant;
import SAAS.UAC.UPR.*;
import SAAS.UAC.UserManagement.TenantAdministrator;
import SAAS.UAC.UserManagement.TenantUser;
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
//                Database.update_Tenant_rolePool(current_user);
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

    // TODO : 完成角色分配
    public static void assign_role(User current_user, User assigned_user, Role role) throws Exception {
        try{
            //0.管理员是否有分配权限，没有则抛出异常
            check_user_permission(current_user,GlobalVariables.ROLE_ASSIGN_CHECK);
            //1.用户ID是否为NULL/空，空则抛出异常
            check_targetUser_null(assigned_user);
            //2.用户是否在用户池中，没有则抛出异常
            //
            // 区分平台和租户
            String[] user_types = current_user.getClass().toString().split("\\.");
            String user_type=user_types[user_types.length - 1];

            if (user_type=="TenantAdministrator") {
                check_tenantUser_in_pool((TenantAdministrator) current_user,(TenantUser) assigned_user);
            }
            //平台不处理
//            else-if (user_type=="PlatformAdministrator"){
//
//            } else {
//                throw new Exception("The user type is not supported");
//            }


            //3.角色ID是否为NULL/空，空则抛出异常
            check_role_null(role);
            //4.角色是否在角色池中，没有则抛出异常
            check_role_in_pool(current_user,role);
            //5.角色是否已在用户roleList中，有则抛出异常
            if(role_in_list(assigned_user,role)){
                throw new IllegalArgumentException("The role already exists in the role list of the user");
            }

        }catch (Exception e) {
            throw e;
        }
        //检查完成，在对应user.roleList中添加
        assigned_user.getRoleList().add(role);
        //TODO:修改数据库
        //

    }

    public static void check_user_permission(User current_user, String Permission) throws Exception{
        if (!current_user.getPermissionPool().contains(GlobalPermission.getPermissionByID(Permission))){
            throw new IllegalArgumentException("The administrator does not have the permission "+Permission);
        }
    }

    public static void check_role_null(Role role) throws Exception{
        String role_ID=role.getRoleID();
        String role_name=role.getRoleName();
        // check whether the role_ID is null, throw an exception if it is null
        if (role_ID == null){
            throw new IllegalArgumentException("The role_ID is null");
        }
        // check whether the role name is null, throw an exception if it is null
        if (role_name == null){
            throw new IllegalArgumentException("The role name is null");
        }
        // check whether the roel_ID is an empty string, throw an exception if it is an empty string
        if (role_ID.equals("")){
            throw new IllegalArgumentException("The role_ID is an empty string");
        }
        // check whether the role_name is an empty string, throw an exception if it is an empty string
        if (role_name.equals("")){
            throw new IllegalArgumentException("The role_name is an empty string");
        }
    }
    public static void check_targetUser_null(User targetUser) throws Exception{
        String user_ID=targetUser.getUserID();
        String user_name=targetUser.getName();
        if(user_ID==null){
            throw new IllegalArgumentException("The user_ID is null");
        }
        if (user_name==null){
            throw new IllegalArgumentException("The user_name is null");
        }
        // check whether the user_ID is an empty string, throw an exception if it is an empty string
        if (user_ID.equals("")){
            throw new IllegalArgumentException("The user_ID is an empty string");
        }
        // check whether the user_name is an empty string, throw an exception if it is an empty string
        if (user_name.equals("")){
            throw new IllegalArgumentException("The user_name is an empty string");
        }

    }

    public static void check_role_in_pool(User current_user,Role role) throws Exception{
        if(!current_user.getRolePool().contains(role)){
            throw new IllegalArgumentException("The role: " + role.getRoleID() +" does not exist in the role pool of you");
        }
    }

    public static void check_tenantUser_in_pool(TenantAdministrator current_user, TenantUser target) throws Exception{
        if(!current_user.getTenantUserList().contains(target)){
            throw new IllegalArgumentException("The user: " + target.getName() +" does not exist in the user pool of you");
        }
    }
    public static boolean role_in_list(User user, Role role){
        return user.getRoleList().contains(role);
    }

    // TODO : 完成角色剥夺
    public static void deprive_role(User current_user, User deprived_user, Role role) throws Exception {
        try{
            //0.管理员是否有分配权限，没有则抛出异常
            check_user_permission(current_user,GlobalVariables.ROLE_DEPRIVE_CHECK);
            //1.用户ID是否为NULL/空，空则抛出异常
            check_targetUser_null(deprived_user);
            //2.用户是否在用户池中，没有则抛出异常
            //
            // 区分平台和租户
            String[] user_types = current_user.getClass().toString().split("\\.");
            String user_type=user_types[user_types.length - 1];

            if (user_type=="TenantAdministrator") {
                check_tenantUser_in_pool((TenantAdministrator) current_user,(TenantUser) deprived_user);
            }
            //平台不处理
//            else-if (user_type=="PlatformAdministrator"){
//
//            } else {
//                throw new Exception("The user type is not supported");
//            }


            //3.角色ID是否为NULL/空，空则抛出异常
            check_role_null(role);
            //4.角色是否在角色池中，没有则抛出异常
            check_role_in_pool(current_user,role);
            //5.角色是否已在用户roleList中，有则抛出异常
            if(!role_in_list(deprived_user,role)){
                throw new IllegalArgumentException("The role is not exists in the role list of the user");
            }

        }catch (Exception e) {
            throw e;
        }
        //检查完成，在对应user.roleList中删除
        deprived_user.getRoleList().remove(role);
        //TODO:修改数据库
        //
    }


}
