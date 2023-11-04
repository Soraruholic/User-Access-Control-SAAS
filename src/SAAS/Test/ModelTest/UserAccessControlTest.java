package SAAS.Test.ModelTest;

import SAAS.Database.Database;
import SAAS.UAC.TenantManagement.PlatformAdministrator;
import SAAS.UAC.TenantManagement.Tenant;
import SAAS.UAC.UPR.*;
import SAAS.UAC.UserAccessControl;
import SAAS.Utils.GlobalVariables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserAccessControlTest {
    @BeforeAll
    public static void create() throws Exception {
        // Database.create();
        // Database.restore();
    }
    @BeforeEach
    public void init() throws Exception {
        Database.Init();
    }
    @Test
    void test_PlatformAdministrator_LoadUAC() throws Exception {
        // Suppose that a PlatformAdministrator has logged in successfully.
        // Then the system might create an object named current_user whose type is PlatformAdministrator.
        // Let the ID of the current_user be "platformAdministrator0".

        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0");

        // Display and check what permissions/roles/services the current_user has ().
        // current_user.display();

        // The platformAdministrator0 is supposed to own FUNC_PER0~9, DATA_PER0~9, ROLE0~14 and SERVICE0~14.
        // Check if the current_user has the permissions/roles/services that he/she is supposed to have.
        HashSet<Permission> permissionList = current_user.getPermissionList();
        HashSet<Role> roleList = current_user.getRoleList();
        HashSet<Service> serviceList = current_user.getServicePool();
        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(permissionList.contains(GlobalPermission.getPermissionByID("FUNC_PER" + i)));
            Assertions.assertTrue(permissionList.contains(GlobalPermission.getPermissionByID("DATA_PER" + i)));
        }
        for (int i = 0; i < 15; i++) {
            Assertions.assertTrue(roleList.contains(GlobalRole.getRoleByID("ROLE" + i)));
            Assertions.assertTrue(serviceList.contains(GlobalService.getServiceByID("SERVICE" + i)));
        }
    }
    @Test
    void test_PlatformAdministrator_AddRole() throws Exception {
        // Then let us suppose that the administrator wants to add a role (to his/her rolePool).
        // Say that the permission to add a role within one's permissionPool to his/her roleService is FUNC_PER0.
        // Then we have to check several things:
        // 0. The administrator has the permission to add a role, throw an exception if not
        // 1. Whether the role already exists in the rolePool, throw an exception if it already exists
        // 2. Whether the role is a global role, throw an exception if it is a global role
        // 3. Whether the permissions of the role are all in the permissionPool, throw an exception if not
        // 4. DO some basic check

        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0");
        List<String> permissionIDList = List.of("FUNC_PER0", "FUNC_PER1", "FUNC_PER2", "FUNC_PER3",
                "FUNC_PER4", "FUNC_PER5", "FUNC_PER6", "FUNC_PER7",
                "FUNC_PER8", "FUNC_PER9");

        // test condition 0
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            GlobalVariables.ROLE_ADD_CHECK = "FUNC_PER11";
            UserAccessControl.add_role(current_user, "ROLE01", "role01", permissionIDList);
        });
        assertEquals("The administrator does not have the permission to add a role", exception.getMessage());

        GlobalVariables.ROLE_ADD_CHECK = "FUNC_PER0";

        // test condition 1
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE1", "role1", permissionIDList);
        });
        assertEquals("The role already exists in the role pool of yours", exception.getMessage());

        // test condition 2
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE15", "role11", permissionIDList);
        });
        assertEquals("The role already exists in the global role pool", exception.getMessage());

        // test condition 3
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE01", "role01", List.of("FUNC_PER0", "FUNC_PER1", "FUNC_PER2", "FUNC_PER3",
                    "FUNC_PER4", "FUNC_PER5", "FUNC_PER6", "FUNC_PER7",
                    "FUNC_PER8", "FUNC_PER9", "FUNC_PER10"));
        });
        assertEquals("The permission: FUNC_PER10 does not exist in the permission pool of you", exception.getMessage());

        // Now do some basic check
        // 1. The role_ID is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, null, "role01", permissionIDList);
        });
        assertEquals("The role_ID is null", exception.getMessage());

        // 2. The role_ID is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "", "role01", permissionIDList);
        });
        assertEquals("The role_ID is an empty string", exception.getMessage());

        // 3. The role_name is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE02", null, permissionIDList);
        });
        assertEquals("The role name is null", exception.getMessage());

        // 4. The role_name is an empty
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE02", "", permissionIDList);
        });
        assertEquals("The role_name is an empty string", exception.getMessage());

        // 5. The permissionIDList is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE02", "role02", null);
        });
        assertEquals("No permissions will be added to the role", exception.getMessage());

        // 6. The permissionIDList is empty
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, "ROLE02", "role02", new ArrayList<>());
        });
        assertEquals("No permissions will be added to the role", exception.getMessage());

        // 7. The Role is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.add_role(current_user, null);
        });
        assertEquals("The role is null", exception.getMessage());

        // Now test whether the database can update the changes correctly
        UserAccessControl.add_role(current_user, "ROLE02", "role02", permissionIDList);

        // Test GlobalRole
        assertEquals(Objects.requireNonNull(GlobalRole.getRoleByID("ROLE02")).getRoleID(), "ROLE02");

        // Test database
        PlatformAdministrator new_user = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0");
        Assertions.assertTrue(new_user.getRolePool().contains(GlobalRole.getRoleByID("ROLE02")));
    }
    @Test
    void test_PlatformAdministrator_DeleteRole() throws Exception {
        // Then Let us suppose that the administrator wants to delete a role from the role pool of his/her
        // Say that the permission to add a role within one's permissionPool to his/her roleService is FUNC_PER1.
        // Similarly, we have to check several things:
        // 0. The administrator has the permission to delete a role, throw an exception if not
        // 1. Whether the role exists in the rolePool, throw an exception if not
        // 2. Whether the role given is the same as the role in the rolePool, throw an exception if not
        // 3. Do some basic check

        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0");
        String deletedRoleID = "ROLE02";
        Role deletedRole = GlobalRole.getRoleByID(deletedRoleID);

        // test condition 0
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            GlobalVariables.ROLE_DELETE_CHECK = "FUNC_PER11";
            UserAccessControl.delete_role(current_user, deletedRole);
        });
        assertEquals("The administrator does not have the permission to delete a role", exception.getMessage());

        GlobalVariables.ROLE_DELETE_CHECK = "FUNC_PER1";

        // test condition 1
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "role20", deletedRole.getPermissionList());
            UserAccessControl.delete_role(current_user, temptRole);
        });
        assertEquals("The role does not exist in the role pool of yours", exception.getMessage());

        // Now do some basic check
        // 1. The role_ID is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("", "role20", deletedRole.getPermissionList());
            UserAccessControl.delete_role(current_user, temptRole);
        });
        assertEquals("The role_ID is an empty string", exception.getMessage());

        // 2. The role_ID is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role(null, "role20", deletedRole.getPermissionList());
            UserAccessControl.delete_role(current_user, temptRole);
        });
        assertEquals("The role_ID is null", exception.getMessage());

        // 3. The role_name is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "", deletedRole.getPermissionList());
            UserAccessControl.delete_role(current_user, temptRole);
        });
        assertEquals("The role_name is an empty string", exception.getMessage());

        // 4. The role_name is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", null, deletedRole.getPermissionList());
            UserAccessControl.delete_role(current_user, temptRole);
        });
        assertEquals("The role name is null", exception.getMessage());

        // 5. The role is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.delete_role(current_user, null);
        });
        assertEquals("The role is null", exception.getMessage());

        // Now test whether the database can update the changes correctly
        UserAccessControl.delete_role(current_user, deletedRole);
        PlatformAdministrator new_user2 = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0");
        // Test whether the role is deleted from the rolePool
        assertFalse(new_user2.getRolePool().contains(GlobalRole.getRoleByID(deletedRoleID)));

        // Delete the role from the GlobalRole to keep the data unchanged
        // Note that even if the platform administrator has deleted certain role, the global role pool does not necessarily have to delete the role
        GlobalRole.deleteRole(deletedRole);
        Database.delete_role_from_Role(deletedRoleID);
    }
    @Test
    void test_PlatformAdministrator_AssignRole() throws Exception {
        // Suppose that the platform administrator wants to assign a role to a tenant.
        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0")                                                                                                                      ;
        Tenant assigned_tenant = Database.select_tenant_byID("TENANT0");
        Role target_role = GlobalRole.getRoleByID("ROLE2");

        // Say that the permission to add a role within one's permissionPool to a tenant's roleList is FUNC_PER2.
        // And we can simply assume that the platform administrator has the permission to add a role to any tenant.
        // Similarly, we have to check several things:
        // 0. The administrator has the permission to assign a role to a tenant, throw an exception if not
        // 1. Whether the tenant exists in the database, throw an exception if not
        // 2. Whether the role exists in the (Global)rolePool, throw an exception if not
        // 3. Whether the role already exists in the roleList of the tenant, throw an exception if so
        // 4. Do some basic check

        // test condition 0
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            GlobalVariables.ROLE_ASSIGN_CHECK = "FUNC_PER21";
            UserAccessControl.assign_role(current_user, assigned_tenant, target_role);
        });
        assertEquals("The administrator does not have the permission to assign a role to a tenant", exception.getMessage());

        GlobalVariables.ROLE_ASSIGN_CHECK = "FUNC_PER2";

        exception = assertThrows(IllegalArgumentException.class, () -> {
            GlobalVariables.ROLE_ASSIGN2TENANT = "FUNC_PER21";
            UserAccessControl.assign_role(current_user, assigned_tenant, target_role);
        });
        assertEquals("The administrator does not have the permission to assign a role to a tenant", exception.getMessage());

        GlobalVariables.ROLE_ASSIGN2TENANT = "FUNC_PER4";

        // test condition 1
        Tenant assigned_tenant2 = Database.select_tenant_byID("TENANT01");
        assertNull(assigned_tenant2);

        // test condition 2
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE02", "role20", target_role.getPermissionList());
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role does not exist in the Global rolePool", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = GlobalRole.getRoleByID("ROLE16");
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role does not exists in the role pool of yours", exception.getMessage());

        // test condition 3
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = GlobalRole.getRoleByID("ROLE1");
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role already exists in the roleList of the assigned user", exception.getMessage());

        // Now do some basic check
        // 1. The role is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.assign_role(current_user, assigned_tenant, null);
        });
        assertEquals("The role is null", exception.getMessage());

        // 2. The role_ID is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role(null, "role20", target_role.getPermissionList());
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_ID is null", exception.getMessage());

        // 3. The role_ID is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("", "role20", target_role.getPermissionList());
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_ID is empty", exception.getMessage());

        // 4. The role_name is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", null, target_role.getPermissionList());
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_name is null", exception.getMessage());

        // 5. The role_name is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "", target_role.getPermissionList());
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_name is empty", exception.getMessage());

        // 6. The permissionList is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "role20", null);
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole.getRoleID(), temptRole.getRoleName(), null);
        });
        assertEquals("The permission_IDs is null", exception.getMessage());

        // 7. The permissionList is empty
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "role20", new HashSet<>());
            UserAccessControl.assign_role(current_user, assigned_tenant, temptRole.getRoleID(), temptRole.getRoleName(), new ArrayList<>());
        });
        assertEquals("The permission_IDs is empty", exception.getMessage());

        // 8. The assigned tenant is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.assign_role(current_user, null, target_role);
        });
        assertEquals("The assigned_user is null", exception.getMessage());


        // Finally, we can test whether the database can update the changes correctly
        UserAccessControl.assign_role(current_user, assigned_tenant, target_role);

        // Check whether the role has been added to the roleList of the assigned tenant
        assigned_tenant2 = Database.select_tenant_byID("TENANT0");
        assertTrue(assigned_tenant2.getRoleList().contains(target_role));
    }
    @Test
    void test_PlatformAdministrator_DepriveRole() throws Exception {
        // Suppose that the platform administrator wants to deprive a role from a tenant.
        PlatformAdministrator current_user = Database.select_platformAdministrator_byID("PLATFORM_ADMIN0")                                                                                                                      ;
        Tenant assigned_tenant = Database.select_tenant_byID("TENANT0");
        Role target_role = GlobalRole.getRoleByID("ROLE1");

        // Say that the permission to deprive a role within one's permissionPool from a tenant's roleList is FUNC_PER3.
        // And we can simply assume that the platform administrator has the permission to deprive a role from any tenant.
        // Similarly, we have to check several things:
        // 0. The administrator has the permission to deprive a role from a tenant, throw an exception if not
        // 1. Whether the tenant exists in the database, throw an exception if not
        // 2. Whether the role exists in the (Global)rolePool, throw an exception if not
        // 3. Whether the role exists in the roleList of the tenant, throw an exception if not
        // 4. Do some basic check

        // test condition 0
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            GlobalVariables.ROLE_DEPRIVE_CHECK = "FUNC_PER31";
            UserAccessControl.deprive_role(current_user, assigned_tenant, target_role);
        });
        assertEquals("The administrator does not have the permission to deprive a role from a tenant", exception.getMessage());

        GlobalVariables.ROLE_DEPRIVE_CHECK = "FUNC_PER7";

        exception = assertThrows(IllegalArgumentException.class, () -> {
            GlobalVariables.ROLE_DEPRIVE_FROM_TENANT = "FUNC_PER31";
            UserAccessControl.deprive_role(current_user, assigned_tenant, target_role);
        });
        assertEquals("The administrator does not have the permission to deprive a role from a tenant", exception.getMessage());

        GlobalVariables.ROLE_DEPRIVE_FROM_TENANT = "FUNC_PER8";

        // test condition 1
        Tenant assigned_tenant2 = Database.select_tenant_byID("TENANT01");
        assertNull(assigned_tenant2);

        // test condition 2
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE02", "role20", target_role.getPermissionList());
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role does not exists in the role pool of yours", exception.getMessage());

        // test condition 3
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = GlobalRole.getRoleByID("ROLE2");
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role does not exists in the roleList of the assigned tenant", exception.getMessage());

        // Now do some basic check
        // 1. The role is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.deprive_role(current_user, assigned_tenant, null);
        });
        assertEquals("The role is null", exception.getMessage());

        // 2. The role_ID is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role(null, "role20", target_role.getPermissionList());
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_ID is null", exception.getMessage());

        // 3. The role_ID is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("", "role20", target_role.getPermissionList());
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_ID is empty", exception.getMessage());

        // 4. The role_name is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", null, target_role.getPermissionList());
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_name is null", exception.getMessage());

        // 5. The role_name is an empty string
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "", target_role.getPermissionList());
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole);
        });
        assertEquals("The role_name is empty", exception.getMessage());

        // 6. The permissionList is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "role20", null);
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole.getRoleID(), temptRole.getRoleName(), null);
        });
        assertEquals("The permission_IDs is null", exception.getMessage());

        // 7. The permissionList is empty
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Role temptRole = new Role("ROLE20", "role20", new HashSet<>());
            UserAccessControl.deprive_role(current_user, assigned_tenant, temptRole.getRoleID(), temptRole.getRoleName(), new ArrayList<>());
        });
        assertEquals("The permission_IDs is empty", exception.getMessage());

        // 8. The assigned tenant is null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            UserAccessControl.deprive_role(current_user, null, target_role);
        });
        assertEquals("The assigned_user is null", exception.getMessage());

        // Finally, we can test whether the database can update the changes correctly
        UserAccessControl.deprive_role(current_user, assigned_tenant, target_role);

        // Check whether the role has been removed from the roleList of the assigned tenant
        assigned_tenant2 = Database.select_tenant_byID("TENANT0");
        assertFalse(assigned_tenant2.getRoleList().contains(target_role));
    }
}