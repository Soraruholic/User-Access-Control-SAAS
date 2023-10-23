package SAAS.UAC.UPR;

import java.util.HashSet;
import java.util.function.Function;

public class FunctionalPermission extends Permission {
    // Store a function that can be called directly
    protected HashSet<String> functionIDList;
    protected PermissionLevel permissionLevel;
    public FunctionalPermission(String permissionID, String permissionName, HashSet<String> functionIDList, PermissionLevel permissionLevel) {
        super(permissionID, permissionName);
        this.functionIDList = functionIDList;
        this.permissionLevel = permissionLevel;
    }

}

