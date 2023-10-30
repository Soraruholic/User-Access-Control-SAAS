package SAAS.UAC.UPR;
import java.util.Map;
import SAAS.Database.Database;

public class GlobalRole {
    private static Map<String, Role> globalRoleMap;

    public static void Init(boolean verbose) throws Exception {
        globalRoleMap = Database.select_roles();
        if (verbose) {
            printAllRoles();
        }
    }

    public static Map<String, Role> getGlobalRoleMap() {
        return globalRoleMap;
    }

    public static Role getRoleByID(String roleID) throws Exception {
        if (globalRoleMap.containsKey(roleID)) {
            return globalRoleMap.get(roleID);
        } else {
            throw new Exception("Role not found");
        }
    }

    // For DEBUG:
    // Print all the roles in the global role map
    private static void printAllRoles() {
        System.out.println("Global Role Map:");
        for (Map.Entry<String, Role> entry : globalRoleMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getRoleName());
        }
    }

}
