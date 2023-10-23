package SAAS.UAC.UPR;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalPermission {
    private static Map<String, Permission> globalPermissionList;
    public static void Init() {
        globalPermissionList = new HashMap<>();
        // select all permissions from the database, and add them to the globalPermissionList
    }
    public static Permission getPermission(String ID) {
        // check whether the permission exists in the globalPermissionList, throw an exception if it does not exist
        if (!globalPermissionList.containsKey(ID)) {
            throw new IllegalArgumentException("Permission does not exist");
        }
        return globalPermissionList.get(ID);
    }
}
