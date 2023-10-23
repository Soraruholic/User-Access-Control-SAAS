package SAAS.UAC.UPR;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DataPermission extends Permission{
    // a member that describes the specific data including the table name and the column name or the row id
    protected String dataLocation;
    protected HashSet<DataOperation> dataOperationList;

    public DataPermission(String permissionID, String permissionName, String dataLocation, HashSet<DataOperation> dataOperationList) {
        super(permissionID, permissionName);
        this.dataLocation = dataLocation;
        this.dataOperationList = dataOperationList;
    }

}
