package SAAS.UAC.UPR;
import java.util.Map;
import SAAS.Database.Database;

public class GlobalPermission {
    private static Map<String, Permission> globalFunctionalPermissionList;
    private static Map<String, Permission> globalDataPermissionList;
    public static void Init(boolean verbose) throws Exception {
        globalFunctionalPermissionList = Database.select_permissions("functional");
        globalDataPermissionList = Database.select_permissions("data");
        if (verbose) {
            printAllPermissions();
        }
    };
    public static Permission getPermissionByID(String permissionID) throws Exception {
        if (globalFunctionalPermissionList.containsKey(permissionID)) {
            return globalFunctionalPermissionList.get(permissionID);
        } else if (globalDataPermissionList.containsKey(permissionID)) {
            return globalDataPermissionList.get(permissionID);
        } else {
            return null;
        }
    }


    // For DEBUG:
    // Print all the permissions in the global permission list
    private static void printAllPermissions() {
        System.out.println("Global Functional Permission List:");
        for (Map.Entry<String, Permission> entry : globalFunctionalPermissionList.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getPermissionName());
        }
        System.out.println("Global Data Permission List:");
        for (Map.Entry<String, Permission> entry : globalDataPermissionList.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getPermissionName());
        }
    }

}
