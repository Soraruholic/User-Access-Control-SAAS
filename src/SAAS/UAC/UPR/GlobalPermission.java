package SAAS.UAC.UPR;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import SAAS.Database.Database;

public class GlobalPermission {
    private static Map<String, Permission> globalFunctionalPermissionList;
    private static Map<String, Permission> globalDataPermissionList;
    public static void Init() throws Exception {
        globalFunctionalPermissionList = Database.select_permissions("functional");

    };

}
