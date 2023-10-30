package SAAS.Database;
import SAAS.UAC.TenantManagement.PlatformAdministrator;
import SAAS.UAC.UPR.*;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/SAAS?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "saas123";

    // Method to create tables (DONE)
    private static void creat_PlatformAdministrator_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deletePlatformAdministratorTable = "DROP TABLE IF EXISTS PlatformAdministrator;";
        String createPlatformAdministratorTable = "CREATE TABLE IF NOT EXISTS PlatformAdministrator (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionPool VARCHAR(255) NOT NULL," +
                "rolePool VARCHAR(255) NOT NULL," +
                "servicePool VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deletePlatformAdministratorTable);
        int count2 = stat.executeUpdate(createPlatformAdministratorTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_Tenant_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteTenantTable = "DROP TABLE IF EXISTS Tenant;";
        String createTenantTable = "CREATE TABLE IF NOT EXISTS Tenant (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionPool VARCHAR(255) NOT NULL," +
                "rolePool VARCHAR(255) NOT NULL," +
                "serviceList VARCHAR(255) NOT NULL," +
                "tenantAdministratorList VARCHAR(255) NOT NULL," +
                "tenantUserList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteTenantTable);
        int count2 = stat.executeUpdate(createTenantTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_TenantAdministrator_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteTenantAdministratorTable = "DROP TABLE IF EXISTS TenantAdministrator;";
        String createTenantAdministratorTable = "CREATE TABLE IF NOT EXISTS TenantAdministrator (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionPool VARCHAR(255) NOT NULL," +
                "rolePool VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteTenantAdministratorTable);
        int count2 = stat.executeUpdate(createTenantAdministratorTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_TenantUser_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteTenantUserTable = "DROP TABLE IF EXISTS TenantUser;";
        String createTenantUserTable = "CREATE TABLE IF NOT EXISTS TenantUser (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionPool VARCHAR(255) NOT NULL," +
                "rolePool VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteTenantUserTable);
        int count2 = stat.executeUpdate(createTenantUserTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_FunctionalPermission_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteFunctionalPermissionTable = "DROP TABLE IF EXISTS FunctionalPermission;";
        String createFunctionalPermissionTable = "CREATE TABLE IF NOT EXISTS FunctionalPermission (" +
                "permissionID VARCHAR(255) NOT NULL," +
                "permissionName VARCHAR(255) NOT NULL," +
                "permissionLevel VARCHAR(255) NOT NULL," +
                "functionIDList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (permissionID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteFunctionalPermissionTable);
        int count2 = stat.executeUpdate(createFunctionalPermissionTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_DataPermission_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteDataPermissionTable = "DROP TABLE IF EXISTS DataPermission;";
        String createDataPermissionTable = "CREATE TABLE IF NOT EXISTS DataPermission (" +
                "permissionID VARCHAR(255) NOT NULL," +
                "permissionName VARCHAR(255) NOT NULL," +
                "dataLocation VARCHAR(255) NOT NULL," +
                "dataOperationList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (permissionID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteDataPermissionTable);
        int count2 = stat.executeUpdate(createDataPermissionTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_Role_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteRoleTable = "DROP TABLE IF EXISTS Role;";
        String createRoleTable = "CREATE TABLE IF NOT EXISTS Role (" +
                "roleID VARCHAR(255) NOT NULL," +
                "roleName VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (roleID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteRoleTable);
        int count2 = stat.executeUpdate(createRoleTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }
    private static void create_Service_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String deleteServiceTable = "DROP TABLE IF EXISTS Service;";
        String createServiceTable = "CREATE TABLE IF NOT EXISTS Service (" +
                "serviceID VARCHAR(255) NOT NULL," +
                "serviceName VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "price VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (serviceID)" +
                ");";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(deleteServiceTable);
        int count2 = stat.executeUpdate(createServiceTable);
        System.out.println(count1);
        System.out.println(count2);
        stat.close();
        con.close();
    }

    // Methods to insert data into tables
    private static void insert_PlatFormAdministrator(PlatformAdministrator user) throws Exception {
        // get the permisionList from the user object and convert it to a string
        String permissionList = "";
        for (Permission permission : user.getPermissionList()) {
            permissionList += permission.getPermissionID() + ";";
        }
        // get the roleList from the user object and convert it to a string
        String roleList = "";
        for (Role role : user.getRoleList()) {
            roleList += role.getRoleID() + ";";
        }
        // get the permissionPool from the user object and convert it to a string
        String permissionPool = "";
        for (Permission permission : user.getPermissionPool()) {
            permissionPool += permission.getPermissionID() + ";";
        }
        // get the rolePool from the user object and convert it to a string
        String rolePool = "";
        for (Role role : user.getRolePool()) {
            rolePool += role.getRoleID() + ";";
        }
        // get the servicePool from the user object and convert it to a string
        String servicePool = "";
        for (Service service : user.getServicePool()) {
            servicePool += service.getServiceID() + ";";
        }

        // insert the user into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertUser = "INSERT INTO PlatformAdministrator VALUES (?,?,?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertUser);
        stat.setString(1, user.getUserID());
        stat.setString(2, user.getName());
        stat.setString(3, permissionList);
        stat.setString(4, roleList);
        stat.setString(5, permissionPool);
        stat.setString(6, rolePool);
        stat.setString(7, servicePool);
        int count = stat.executeUpdate();
        System.out.println(count);
        stat.close();
        con.close();
    }


    // TODO: insert_functional_permission
    // TODO: insert_data_permission
    // TODO: insert_role
    // TODO: insert_service
    // TODO: insert_Tenant
    // TODO: insert_TenantAdministrator
    // TODO: insert_TenantUser






    // Methods to select data from tables
    // TODO: select_permissions
    public static Map<String, Permission> select_permissions(String permissionType) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String tableName = permissionType.equals("functional") ? "FunctionalPermission" : "DataPermission" ;
        String selectPermission = "SELECT * FROM " + tableName + ";";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectPermission);
        Map<String, Permission> permissionMap = new HashMap<>();
        if (permissionType.equals("functional")) {
            while (rs.next()) {
                String permissionID = rs.getString("permissionID");
                String permissionName = rs.getString("permissionName");
                String permissionLevel = rs.getString("permissionLevel");
                String functionIDList = rs.getString("functionIDList");
                PermissionLevel _permissionLevel = PermissionLevel.valueOf(permissionLevel);
                String[] functionIDs = functionIDList.split(";");
                HashSet<String> _functionIDList = new HashSet<>(Arrays.asList(functionIDs));
                Permission permission = new FunctionalPermission(permissionID, permissionName, _functionIDList, _permissionLevel);
                permissionMap.put(permissionID, permission);
            }
        } else {
            while (rs.next()) {
                String permissionID = rs.getString("permissionID");
                String permissionName = rs.getString("permissionName");
                String dataLocation = rs.getString("dataLocation");
                String dataOperationList = rs.getString("dataOperationList");
                // split the dataOperationList by ';' and change every element into DataOperation
                String[] dataOperations = dataOperationList.split(";");
                HashSet<DataOperation> _dataOperationList = new HashSet<>();
                for (String dataOperation : dataOperations) {
                    _dataOperationList.add(DataOperation.valueOf(dataOperation));
                }
                Permission permission = new DataPermission(permissionID, permissionName, dataLocation, _dataOperationList);
                permissionMap.put(permissionID, permission);
            }
        }

        rs.close();
        stat.close();
        con.close();
        return permissionMap;
    }



    // create tables in the database (DONE)
    public static void create() throws Exception {
        creat_PlatformAdministrator_table();
        create_Tenant_table();
        create_TenantAdministrator_table();
        create_TenantUser_table();
        create_FunctionalPermission_table();
        create_DataPermission_table();
        create_Role_table();
        create_Service_table();
    }

    // TODO: initialize
    public static void initialize() {
        // Prepare test data

        // Insert the data into the database

    }
    public static void main(String[] args) throws Exception {
        create();
        insert_PlatFormAdministrator(new PlatformAdministrator("admin", "123", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
    }

}
