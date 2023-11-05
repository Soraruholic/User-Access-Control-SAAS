package SAAS.Test.ModelTest;

// Deprecated


//
//import SAAS.Database.Database;
//import SAAS.Test.Test;
//import SAAS.UAC.TenantManagement.PlatformAdministrator;
//import SAAS.UAC.UPR.*;
//import SAAS.UAC.UserAccessControl;
//import SAAS.Utils.GlobalVariables;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class ModelTest extends Test{
//    public static void main(String[] args) throws Exception {
//        // Load the databases
//        Database.Init();
//
//        // throw an exception if the test fails
//        if (!testcase_1()) {
//            throw new Exception("Test 1 failed");
//        } else {
//            System.out.println("Test 1 passed");
//        }
//        if (!testcase_2()) {
//            throw new Exception("Test 2 failed");
//        } else {
//            System.out.println("Test 2 passed");
//        }
//        if (!testcase_3()) {
//            throw new Exception("Test 3 failed");
//        } else {
//            System.out.println("Test 3 passed");
//        }
//    }
//
//    //----------------------------------Tests for PlatformAdministrator's Behavior------------------------------------//
//    // Test 1 is mainly about cases where a PlatformAdministrator logs in.
//    private static boolean testcase_1 () throws Exception {
//        // Suppose that a PlatformAdministrator has logged in successfully.
//        // Then the system might create an object named current_user whose type is PlatformAdministrator.
//        // Let the ID of the current_user be "platformAdministrator0".
//
//        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("platformAdministrator0");
//
//        // Display and check what permissions/roles/services the current_user has ().
//        // current_user.display();
//
//        // The platformAdministrator0 is supposed to own FUNC_PER0~9, DATA_PER0~9, ROLE0~14 and SERVICE0~14.
//        // Check if the current_user has the permissions/roles/services that he/she is supposed to have.
//        HashSet<Permission> permissionList = current_user.getPermissionList();
//        HashSet<Role> roleList = current_user.getRoleList();
//        HashSet<Service> serviceList = current_user.getServicePool();
//        for (int i = 0; i < 10; i++) {
//            if (!permissionList.contains(GlobalPermission.getPermissionByID("FUNC_PER" + i))) {
//                System.out.println("Error: The current_user does not have FUNC_PER" + i + ".");
//                return false;
//            }
//            if (!permissionList.contains(GlobalPermission.getPermissionByID("DATA_PER" + i))) {
//                System.out.println("Error: The current_user does not have DATA_PER" + i + ".");
//                return false;
//            }
//        }
//        for (int i = 0; i < 15; i++) {
//            if (!roleList.contains(GlobalRole.getRoleByID("ROLE" + i))) {
//                System.out.println("Error: The current_user does not have ROLE" + i + ".");
//                return false;
//            }
//            if (!serviceList.contains(GlobalService.getServiceByID("SERVICE" + i))) {
//                System.out.println("Error: The current_user does not have SERVICE" + i + ".");
//                return false;
//            }
//        }
//
//        return true;
//    }
//    // Test 2 is mainly about cases where a PlatformAdministrator wants to add a role.
//    private static boolean testcase_2() throws Exception {
//
//        // Then let us suppose that the administrator wants to add a role (to his/her rolePool).
//        // Say that the permission to add a role within one's permissionPool to his/her roleService is FUNC_PER0.
//        // Then we have to check several things:
//        // 0. The administrator has the permission to add a role, throw an exception if not
//        // 1. Whether the role already exists in the rolePool, throw an exception if it already exists
//        // 2. Whether the role is a global role, throw an exception if it is a global role
//        // 3. Whether the permissions of the role are all in the permissionPool, throw an exception if not
//        // 4. DO some basic check
//
//        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("platformAdministrator0");
//        List<String> permissionIDList = List.of("FUNC_PER0", "FUNC_PER1", "FUNC_PER2", "FUNC_PER3",
//                "FUNC_PER4", "FUNC_PER5", "FUNC_PER6", "FUNC_PER7",
//                "FUNC_PER8", "FUNC_PER9");
//        try {
//            // test condition 0
//            GlobalVariables.ROLE_ADD_CHECK = "FUNC_PER11";
//            UserAccessControl.add_role(current_user, "ROLE01", "role01", permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The administrator does not have the permission to add a role")) {
//                return false;
//            }
//        }
//
//        GlobalVariables.ROLE_ADD_CHECK = "FUNC_PER0";
//
//        try {
//            // test condition 1
//            UserAccessControl.add_role(current_user, "ROLE1", "role1", permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role already exists in the role pool of yours")) {
//                return false;
//            }
//        }
//
//        try {
//            // test condition 2
//            UserAccessControl.add_role(current_user, "ROLE15", "role11", permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            // System.out.println(e.toString());
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role already exists in the global role pool")) {
//                return false;
//            }
//        }
//
//        try {
//            // test condition 3
//            UserAccessControl.add_role(current_user, "ROLE01", "role01", List.of("FUNC_PER0", "FUNC_PER1", "FUNC_PER2", "FUNC_PER3",
//                    "FUNC_PER4", "FUNC_PER5", "FUNC_PER6", "FUNC_PER7",
//                    "FUNC_PER8", "FUNC_PER9", "FUNC_PER10"));
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The permission: FUNC_PER10 does not exist in the permission pool of you")) {
//                return false;
//            }
//        }
//
//        // Now do some basic check
//        // 1. The role_ID is null
//        try {
//            UserAccessControl.add_role(current_user, null, "role01", permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role_ID is null")) {
//                return false;
//            }
//        }
//        // 2. The role_ID is an empty string
//        try {
//            UserAccessControl.add_role(current_user, "", "role01", permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role_ID is an empty string")) {
//                return false;
//            }
//        }
//        // 3. The role_name is null
//        try {
//            UserAccessControl.add_role(current_user, "ROLE02", null, permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role name is null")) {
//                return false;
//            }
//        }
//        // 4. The role_name is an empty string
//        try {
//            UserAccessControl.add_role(current_user, "ROLE02", "", permissionIDList);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role_name is an empty string")) {
//                return false;
//            }
//        }
//        // 5. The permissionIDList is null
//        try {
//            UserAccessControl.add_role(current_user, "ROLE02", "role02", null);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: No permissions will be added to the role")) {
//                return false;
//            }
//        }
//        // 6. The permissionIDList is empty
//        try {
//            UserAccessControl.add_role(current_user, "ROLE02", "role02", new ArrayList<>());
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: No permissions will be added to the role")) {
//                return false;
//            }
//        }
//        // 7. The Role is null
//        try {
//            UserAccessControl.add_role(current_user, null);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role is null")) {
//                return false;
//            }
//        }
//
//        // Now test whether the database can update the changes correctly
//        UserAccessControl.add_role(current_user, "ROLE02", "role02", permissionIDList);
//
//        // Test GlobalRole
//        if (GlobalRole.getRoleByID("ROLE02") == null) {
//            return false;
//        }
//        // Test PlatformAdministrator
//        PlatformAdministrator new_user = Database.select_platformAdministrator_byID("platformAdministrator0");
//        if (!new_user.getRolePool().contains(GlobalRole.getRoleByID("ROLE02"))) {
//            return false;
//        }
//        return true;
//    }
//    // Test 3 is mainly about cases where a PlatformAdministrator wants to delete a role from the role pool.
//    private static boolean testcase_3() throws Exception {
//
//        // Then Let us suppose that the administrator wants to delete a role from the role pool of his/her
//        // Say that the permission to add a role within one's permissionPool to his/her roleService is FUNC_PER1.
//        // Similarly, we have to check several things:
//        // 0. The administrator has the permission to delete a role, throw an exception if not
//        // 1. Whether the role exists in the rolePool, throw an exception if not
//        // 2. Whether the role given is the same as the role in the rolePool, throw an exception if not
//        // 3. Do some basic check
//
//        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("platformAdministrator0");
//        String deletedRoleID = "ROLE02";
//        Role deletedRole = GlobalRole.getRoleByID(deletedRoleID);
//        try {
//            // test condition 0
//            GlobalVariables.ROLE_DELETE_CHECK = "FUNC_PER11";
//            UserAccessControl.delete_role(current_user, deletedRole);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The administrator does not have the permission to delete a role")) {
//                return false;
//            }
//        }
//
//        GlobalVariables.ROLE_DELETE_CHECK = "FUNC_PER1";
//
//        try {
//            // test condition 1
//            Role temptRole = new Role("ROLE20", "role20", deletedRole.getPermissionList());
//            UserAccessControl.delete_role(current_user, temptRole);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role does not exist in the role pool of yours")) {
//                return false;
//            }
//        }
//
//        // Now do some basic check
//        // 1. The role_ID is an empty string
//        try {
//            Role temptRole = new Role("", "role20", deletedRole.getPermissionList());
//            UserAccessControl.delete_role(current_user, temptRole);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role_ID is an empty string")) {
//                return false;
//            }
//        }
//        // 2. The role_ID is null
//        try {
//            Role temptRole = new Role(null, "role20", deletedRole.getPermissionList());
//            UserAccessControl.delete_role(current_user, temptRole);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role_ID is null")) {
//                return false;
//            }
//        }
//        // 3. The role_name is an empty string
//        try {
//            Role temptRole = new Role("ROLE20", "", deletedRole.getPermissionList());
//            UserAccessControl.delete_role(current_user, temptRole);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role_name is an empty string")) {
//                return false;
//            }
//        }
//        // 4. The role_name is null
//        try {
//            Role temptRole = new Role("ROLE20", null, deletedRole.getPermissionList());
//            UserAccessControl.delete_role(current_user, temptRole);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role name is null")) {
//                return false;
//            }
//        }
//        // 5. The role is null
//        try {
//            UserAccessControl.delete_role(current_user, null);
//            return false;
//        } catch (IllegalArgumentException e) {
//            if (!e.toString().equals("java.lang.IllegalArgumentException: The role is null")) {
//                return false;
//            }
//        }
//
//        // Now test whether the database can update the changes correctly
//        UserAccessControl.delete_role(current_user, deletedRole);
//        PlatformAdministrator new_user2 = Database.select_platformAdministrator_byID("platformAdministrator0");
//        // Test whether the role is deleted from the rolePool
//        if (new_user2.getRolePool().contains(GlobalRole.getRoleByID(deletedRoleID))) {
//            return false;
//        }
//
//        // Delete the role from the GlobalRole to keep the data unchanged
//        GlobalRole.deleteRole(deletedRole);
//        Database.delete_role_from_Role(deletedRoleID);
//        return true;
//    }
//    // Test 4 is mainly about cases where a PlatformAdministrator wants to assign a role to a tenant.
//    private static boolean testcase_4() throws Exception {
//
//        return true;
//    }
//
//    //------------------------------------End of PlatformAdministratorTest--------------------------------------------//
//}
