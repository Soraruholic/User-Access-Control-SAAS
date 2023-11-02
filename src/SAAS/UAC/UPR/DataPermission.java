package SAAS.UAC.UPR;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DataPermission extends Permission{
    // a member that describes the specific data including the table name and the column name or the row id
    protected String dataLocation;
    // for example, dataLocation of the sign in history : "LogIn;UserID;CheckOrNot;"
    // select CheckOrNot from LogIn where userID = UserID
    // select [ColumnNames] from [TableNames] where [RowNames]  <=Parser=> dataLocation("TableNames;RowNames;ColumnNames;")
    protected HashSet<DataOperation> dataOperationList;

    public DataPermission(String permissionID, String permissionName, String dataLocation, HashSet<DataOperation> dataOperationList) {
        super(permissionID, permissionName);
        this.dataLocation = dataLocation;
        this.dataOperationList = dataOperationList;
    }

    public String getDataLocation() {
        return dataLocation;
    }
    public HashSet<DataOperation> getDataOperationList() {
        return dataOperationList;
    }

}
