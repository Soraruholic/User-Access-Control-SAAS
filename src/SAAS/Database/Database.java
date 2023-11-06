package SAAS.Database;
import SAAS.UAC.LogIn.LogUser;
import SAAS.UAC.TenantManagement.PlatformAdministrator;
import SAAS.UAC.TenantManagement.Tenant;
import SAAS.UAC.UPR.*;
import SAAS.UAC.UserManagement.TenantAdministrator;
import SAAS.UAC.UserManagement.TenantUser;

import java.security.spec.ECField;
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


    // --------------------------------------Method to create tables (DONE)-------------------------------------------//
    private static void drop_tables() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String dropFunctionalPermissionTable = "DROP TABLE IF EXISTS FunctionalPermission;";
        String dropDataPermissionTable = "DROP TABLE IF EXISTS DataPermission;";
        String dropRoleTable = "DROP TABLE IF EXISTS Role;";
        String dropServiceTable = "DROP TABLE IF EXISTS Service;";
        String dropPlatformAdministratorTable = "DROP TABLE IF EXISTS PlatformAdministrator;";
        String dropTenantAdministratorTable = "DROP TABLE IF EXISTS TenantAdministrator;";
        String dropTenantUserTable = "DROP TABLE IF EXISTS TenantUser;";
        String dropLogUserTable = "DROP TABLE IF EXISTS LogUser;";
        String dropTenantTable = "DROP TABLE IF EXISTS Tenant;";
        Statement stat = con.createStatement();
        int count1 = stat.executeUpdate(dropFunctionalPermissionTable);
        int count2 = stat.executeUpdate(dropRoleTable);
        int count3 = stat.executeUpdate(dropServiceTable);
        int count4 = stat.executeUpdate(dropPlatformAdministratorTable);
        int count6 = stat.executeUpdate(dropTenantAdministratorTable);
        int count7 = stat.executeUpdate(dropTenantUserTable);
        int count8 = stat.executeUpdate(dropLogUserTable);
        int count5 = stat.executeUpdate(dropTenantTable);
        int count9 = stat.executeUpdate(dropDataPermissionTable);
        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 0 && count7 == 0 && count8 == 0 && count9 == 0) {
            System.out.println("All tables dropped successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_PlatformAdministrator_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
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
        int count = stat.executeUpdate(createPlatformAdministratorTable);
        if (count == 0) {
            System.out.println("PlatformAdministrator table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_Tenant_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
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
        int count = stat.executeUpdate(createTenantTable);
        if (count == 0) {
            System.out.println("Tenant table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_TenantAdministrator_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String createTenantAdministratorTable = "CREATE TABLE IF NOT EXISTS TenantAdministrator (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionPool VARCHAR(255) NOT NULL," +
                "rolePool VARCHAR(255) NOT NULL," +
                "tenantID VARCHAR(255) NOT NULL," +
                "FOREIGN KEY (tenantID) REFERENCES Tenant(userID)," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createTenantAdministratorTable);
        if (count == 0) {
            System.out.println("TenantAdministrator table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_TenantUser_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String createTenantUserTable = "CREATE TABLE IF NOT EXISTS TenantUser (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionPool VARCHAR(255) NOT NULL," +
                "rolePool VARCHAR(255) NOT NULL," +
                "tenantID VARCHAR(255)," +
                "FOREIGN KEY (tenantID) REFERENCES Tenant(userID)," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createTenantUserTable);
        if (count == 0) {
            System.out.println("TenantUser table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_FunctionalPermission_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String createFunctionalPermissionTable = "CREATE TABLE IF NOT EXISTS FunctionalPermission (" +
                "permissionID VARCHAR(255) NOT NULL," +
                "permissionName VARCHAR(255) NOT NULL," +
                "permissionLevel VARCHAR(255) NOT NULL," +
                "functionIDList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (permissionID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createFunctionalPermissionTable);
        if (count == 0) {
            System.out.println("FunctionalPermission table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_DataPermission_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String createDataPermissionTable = "CREATE TABLE IF NOT EXISTS DataPermission (" +
                "permissionID VARCHAR(255) NOT NULL," +
                "permissionName VARCHAR(255) NOT NULL," +
                "dataLocation VARCHAR(255) NOT NULL," +
                "dataOperationList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (permissionID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createDataPermissionTable);
        if (count == 0) {
            System.out.println("DataPermission table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_Role_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String createRoleTable = "CREATE TABLE IF NOT EXISTS Role (" +
                "roleID VARCHAR(255) NOT NULL," +
                "roleName VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (roleID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createRoleTable);
        if (count == 0) {
            System.out.println("Role table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_Service_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        // permissionList VARCHAR(255) = "permisionID1;...;permisionIDn";
        String createServiceTable = "CREATE TABLE IF NOT EXISTS Service (" +
                "serviceID VARCHAR(255) NOT NULL," +
                "serviceName VARCHAR(255) NOT NULL," +
                "roleList VARCHAR(255) NOT NULL," +
                "permissionList VARCHAR(255) NOT NULL," +
                "price VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (serviceID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createServiceTable);
        if (count == 0) {
            System.out.println("Service table created successfully");
        }
        stat.close();
        con.close();
    }
    private static void create_UserBasic_table() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

        String createUserBasicTable = "CREATE TABLE IF NOT EXISTS UserBasic (" +
                "userID VARCHAR(255) NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL," +
                "phone VARCHAR(255) NOT NULL," +
                "lastPSWChange VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (userID)" +
                ");";
        Statement stat = con.createStatement();
        int count = stat.executeUpdate(createUserBasicTable);
        if (count == 0) {
            System.out.println("UserBasic table created successfully");
        }
        stat.close();
        con.close();
    }
    // ---------------------------------------------End of The Section------------------------------------------------//


    // ---------------------------------Methods to insert data into tables--------------------------------------------//
    private static void insert_PlatformAdministrator(PlatformAdministrator user, boolean verbose) throws Exception {
        // get the permisionList from the user object and convert it to a string
        StringBuilder permissionList = new StringBuilder();
        for (Permission permission : user.getPermissionList()) {
            permissionList.append(permission.getPermissionID()).append(";");
        }
        // get the roleList from the user object and convert it to a string
        StringBuilder roleList = new StringBuilder();
        for (Role role : user.getRoleList()) {
            roleList.append(role.getRoleID()).append(";");
        }
        // get the permissionPool from the user object and convert it to a string
        StringBuilder permissionPool = new StringBuilder();
        for (Permission permission : user.getPermissionPool()) {
            permissionPool.append(permission.getPermissionID()).append(";");
        }
        // get the rolePool from the user object and convert it to a string
        StringBuilder rolePool = new StringBuilder();
        for (Role role : user.getRolePool()) {
            rolePool.append(role.getRoleID()).append(";");
        }
        // get the servicePool from the user object and convert it to a string
        StringBuilder servicePool = new StringBuilder();
        for (Service service : user.getServicePool()) {
            servicePool.append(service.getServiceID()).append(";");
        }

        // insert the user into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertUser = "INSERT INTO PlatformAdministrator VALUES (?,?,?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertUser);
        stat.setString(1, user.getUserID());
        stat.setString(2, user.getName());
        stat.setString(3, permissionList.toString());
        stat.setString(4, roleList.toString());
        stat.setString(5, permissionPool.toString());
        stat.setString(6, rolePool.toString());
        stat.setString(7, servicePool.toString());
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert PlatformAdministrator successfully!");
                // print the userID and userName
                System.out.println("\tID: " + user.getUserID());
                System.out.println("\tName: " + user.getName());
            } else {
                System.out.println("Insert PlatformAdministrator failed!");
            }
        }
        stat.close();
        con.close();
    }
    // (DONE) insert_functional_permission
    private static void insert_FunctionalPermission(FunctionalPermission permission, boolean verbose) throws Exception {
        // get the functionIDList from the permission object and convert it to a string
        StringBuilder functionIDList = new StringBuilder();
        for (String functionID : permission.getFunctionIDList()) {
            functionIDList.append(functionID).append(";");
        }
        // insert the permission into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertPermission = "INSERT INTO FunctionalPermission VALUES (?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertPermission);
        stat.setString(1, permission.getPermissionID());
        stat.setString(2, permission.getPermissionName());
        stat.setString(3, permission.getPermissionLevel().toString());
        stat.setString(4, functionIDList.toString());
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert FunctionalPermission successfully!");
                // print the permissionID and permissionName
                System.out.println("\tID: " + permission.getPermissionID());
                System.out.println("\tName: " + permission.getPermissionName());
            } else {
                System.out.println("Insert FunctionalPermission failed!");
            }
        }
        stat.close();
        con.close();
    }
    // (DONE) insert_data_permission
    private static void insert_DataPermission(DataPermission permission, boolean verbose) throws Exception {
        // get the dataIDList from the permission object and convert it to a string
        StringBuilder dataIDList = new StringBuilder();
        for (DataOperation dataID : permission.getDataOperationList()) {
            dataIDList.append(dataID.toString()).append(";");
        }
        // insert the permission into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertPermission = "INSERT INTO DataPermission VALUES (?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertPermission);
        stat.setString(1, permission.getPermissionID());
        stat.setString(2, permission.getPermissionName());
        stat.setString(3, permission.getDataLocation());
        stat.setString(4, dataIDList.toString());
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert DataPermission successfully!");
                // print the permissionID and permissionName
                System.out.println("\tID: " + permission.getPermissionID());
                System.out.println("\tName: " + permission.getPermissionName());
            } else {
                System.out.println("Insert DataPermission failed!");
            }
        }
        stat.close();
        con.close();
    }
    // (DONE) insert_role
    public static void insert_Role(Role role, boolean verbose) throws Exception {
        // get the permissionList from the role object and convert it to a string
        StringBuilder permissionList = new StringBuilder();
        for (Permission permission : role.getPermissionList()) {
            permissionList.append(permission.getPermissionID()).append(";");
        }
        // insert the role into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertRole = "INSERT INTO Role VALUES (?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertRole);
        stat.setString(1, role.getRoleID());
        stat.setString(2, role.getRoleName());
        stat.setString(3, permissionList.toString());
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert Role successfully!");
                // print the roleID and roleName
                System.out.println("\tID: " + role.getRoleID());
                System.out.println("\tName: " + role.getRoleName());
            } else {
                System.out.println("Insert Role failed!");
            }
        }
        stat.close();
        con.close();
    }
    // (DONE) insert_service
    private static void insert_Service(Service service, boolean verbose) throws Exception {
        // get the roleList from the service object and convert it to a string
        StringBuilder roleList = new StringBuilder();
        if (service.getRoleList() != null) {
            for (Role role : service.getRoleList()) {
                roleList.append(role.getRoleID()).append(";");
            }
        }
        // get the permissionList from the service object and convert it to a string
        StringBuilder permissionList = new StringBuilder();
        if (service.getPermissionList() != null) {
            for (Permission permission : service.getPermissionList()) {
                permissionList.append(permission.getPermissionID()).append(";");
            }
        }

        // insert the service into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertService = "INSERT INTO Service VALUES (?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertService);
        stat.setString(1, service.getServiceID());
        stat.setString(2, service.getServiceName());
        stat.setString(3, roleList.toString());
        stat.setString(4, permissionList.toString());
        stat.setString(5, service.getPrice());
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert Service successfully!");
                // print the serviceID and serviceName
                System.out.println("\tID: " + service.getServiceID());
                System.out.println("\tName: " + service.getServiceName());
            } else {
                System.out.println("Insert Service failed!");
            }
        }
        stat.close();
        con.close();
    }
    // TODO: insert_Tenant
    private static void insert_Tenant(Tenant tenant, boolean verbose) throws Exception {
        // get the serviceList from the tenant object and convert it to a string
        StringBuilder serviceList = new StringBuilder();
        if (tenant.getServiceList() != null) {
            for (Service service : tenant.getServiceList()) {
                serviceList.append(service.getServiceID()).append(";");
            }
        }
        // get the permissionList from the tenant object and convert it to a string
        StringBuilder permissionList = new StringBuilder();
        if (tenant.getPermissionList() != null) {
            for (Permission permission : tenant.getPermissionList()) {
                permissionList.append(permission.getPermissionID()).append(";");
            }
        }
        // get the roleList from the tenant object and convert it to a string
        StringBuilder roleList = new StringBuilder();
        if (tenant.getRoleList() != null) {
            for (Role role : tenant.getRoleList()) {
                roleList.append(role.getRoleID()).append(";");
            }
        }
        // get the permissionPool from the tenant object and convert it to a string
        StringBuilder permissionPool = new StringBuilder();
        if (tenant.getPermissionPool() != null) {
            for (Permission permission : tenant.getPermissionPool()) {
                permissionPool.append(permission.getPermissionID()).append(";");
            }
        }
        // get the rolePool from the tenant object and convert it to a string
        StringBuilder rolePool = new StringBuilder();
        if (tenant.getRolePool() != null) {
            for (Role role : tenant.getRolePool()) {
                rolePool.append(role.getRoleID()).append(";");
            }
        }
        // get the tenantAdministratorList from the tenant object and convert it to a string
        StringBuilder tenantAdministratorList = new StringBuilder();
        if (tenant.getTenantAdministratorList() != null) {
            for (String tenantAdministrator : tenant.getTenantAdministratorList()) {
                tenantAdministratorList.append(tenantAdministrator).append(";");
            }
        }
        // get the tenantUserList from the tenant object and convert it to a string
        StringBuilder tenantUserList = new StringBuilder();
        if (tenant.getTenantUserList() != null) {
            for (String tenantUser : tenant.getTenantUserList()) {
                tenantUserList.append(tenantUser).append(";");
            }
        }

        // insert the tenant into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertTenant = "INSERT INTO Tenant VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertTenant);
        stat.setString(1, tenant.getUserID());
        stat.setString(2, tenant.getName());
        stat.setString(3, permissionList.toString());
        stat.setString(4, roleList.toString());
        stat.setString(5, permissionPool.toString());
        stat.setString(6, rolePool.toString());
        stat.setString(7, serviceList.toString());
        stat.setString(8, tenantAdministratorList.toString());
        stat.setString(9, tenantUserList.toString());
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert Tenant successfully!");
                // print the tenantID and tenantName
                System.out.println("\tID: " + tenant.getUserID());
                System.out.println("\tName: " + tenant.getName());
            } else {
                System.out.println("Insert Tenant failed!");
            }
        }
        stat.close();
        con.close();
    }
    // TODO: insert_TenantAdministrator
    private static void insert_TenantAdministrator(Tenant tenantAdministrator, String tenantID, boolean verbose) throws Exception {
        // get the tenantList from the tenantAdministrator object and convert it to a string
        StringBuilder permissionPool = new StringBuilder();
        if (tenantAdministrator.getPermissionPool() != null) {
            for (Permission permission : tenantAdministrator.getPermissionPool()) {
                permissionPool.append(permission.getPermissionID()).append(";");
            }
        }
        // get the serviceList from the tenantAdministrator object and convert it to a string
        StringBuilder rolePool = new StringBuilder();
        if (tenantAdministrator.getRolePool() != null) {
            for (Role role : tenantAdministrator.getRolePool()) {
                rolePool.append(role.getRoleID()).append(";");
            }
        }
        // get the permissionList from the tenantAdministrator object and convert it to a string
        StringBuilder permissionList = new StringBuilder();
        if (tenantAdministrator.getPermissionList() != null) {
            for (Permission permission : tenantAdministrator.getPermissionList()) {
                permissionList.append(permission.getPermissionID()).append(";");
            }
        }
        // get the roleList from the tenantAdministrator object and convert it to a string
        StringBuilder roleList = new StringBuilder();
        if (tenantAdministrator.getRoleList() != null) {
            for (Role role : tenantAdministrator.getRoleList()) {
                roleList.append(role.getRoleID()).append(";");
            }
        }

        // insert the tenantAdministrator into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertTenantAdministrator = "INSERT INTO TenantAdministrator VALUES (?,?,?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertTenantAdministrator);
        stat.setString(1, tenantAdministrator.getUserID());
        stat.setString(2, tenantAdministrator.getName());
        stat.setString(3, permissionList.toString());
        stat.setString(4, roleList.toString());
        stat.setString(5, permissionPool.toString());
        stat.setString(6, rolePool.toString());
        stat.setString(7, tenantID);
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert TenantAdministrator successfully!");
                // print the tenantAdministratorID and tenantAdministratorName
                System.out.println("\tID: " + tenantAdministrator.getUserID());
                System.out.println("\tName: " + tenantAdministrator.getName());
            } else {
                System.out.println("Insert TenantAdministrator failed!");
            }
        }
        stat.close();
        con.close();
    }
    // TODO: insert_TenantUser
    private static void insert_TenantUser(Tenant tenantUser, String tenantID, boolean verbose) throws Exception {
        // get the tenantList from the tenantUser object and convert it to a string
        StringBuilder permissionPool = new StringBuilder();
        if (tenantUser.getPermissionPool() != null) {
            for (Permission permission : tenantUser.getPermissionPool()) {
                permissionPool.append(permission.getPermissionID()).append(";");
            }
        }
        // get the serviceList from the tenantUser object and convert it to a string
        StringBuilder rolePool = new StringBuilder();
        if (tenantUser.getRolePool() != null) {
            for (Role role : tenantUser.getRolePool()) {
                rolePool.append(role.getRoleID()).append(";");
            }
        }
        // get the permissionList from the tenantUser object and convert it to a string
        StringBuilder permissionList = new StringBuilder();
        if (tenantUser.getPermissionList() != null) {
            for (Permission permission : tenantUser.getPermissionList()) {
                permissionList.append(permission.getPermissionID()).append(";");
            }
        }
        // get the roleList from the tenantUser object and convert it to a string
        StringBuilder roleList = new StringBuilder();
        if (tenantUser.getRoleList() != null) {
            for (Role role : tenantUser.getRoleList()) {
                roleList.append(role.getRoleID()).append(";");
            }
        }

        // insert the tenantUser into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertTenantUser = "INSERT INTO TenantUser VALUES (?,?,?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertTenantUser);
        stat.setString(1, tenantUser.getUserID());
        stat.setString(2, tenantUser.getName());
        stat.setString(3, permissionList.toString());
        stat.setString(4, roleList.toString());
        stat.setString(5, permissionPool.toString());
        stat.setString(6, rolePool.toString());
        stat.setString(7, tenantID);
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert TenantUser successfully!");
                // print the tenantUserID and tenantUserName
                System.out.println("\tID: " + tenantUser.getUserID());
                System.out.println("\tName: " + tenantUser.getName());
            } else {
                System.out.println("Insert TenantUser failed!");
            }
        }
        stat.close();
        con.close();
    }
    public static void insert_userBasic(String user_ID, String userName, String PSW, String email, String phoneNumber, String lastPSWChange, boolean verbose) throws Exception {
        // insert the userBasic into the table
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String insertUserBasic = "INSERT INTO UserBasic VALUES (?,?,?,?,?,?);";
        PreparedStatement stat = con.prepareStatement(insertUserBasic);
        stat.setString(1, user_ID);
        stat.setString(2, userName);
        stat.setString(3, PSW);
        stat.setString(4, email);
        stat.setString(5, phoneNumber);
        stat.setString(6, lastPSWChange);
        int count = stat.executeUpdate();
        if (verbose) {
            if (count == 1) {
                System.out.println("Insert UserBasic successfully!");
                // print the userID and userName
                System.out.println("\tID: " + user_ID);
                System.out.println("\tName: " + userName);
            } else {
                System.out.println("Insert UserBasic failed!");
            }
        }
        stat.close();
        con.close();
    }
    // ---------------------------------------------End of The Section------------------------------------------------//



    // -----------------------------------Methods to select data from tables------------------------------------------//
    // (DONE) select_permissions
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
                // If the dataOperationList is not empty, add the dataOperation into the _dataOperationList
                if (!dataOperationList.equals("")) {
                    for (String dataOperation : dataOperations) {
                        _dataOperationList.add(DataOperation.valueOf(dataOperation));
                    }
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
    // (DONE) select_roles
    public static Map<String, Role> select_roles() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectRole = "SELECT * FROM Role;";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectRole);
        Map<String, Role> roleMap = new HashMap<>();
        while (rs.next()) {
            String roleID = rs.getString("roleID");
            String roleName = rs.getString("roleName");
            String permissionList = rs.getString("permissionList");
            HashSet<Permission> _permissionList = new HashSet<>();
            if (!permissionList.equals("")) {
                String[] permissions = permissionList.split(";");
                for (String permission : permissions) {
                    _permissionList.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            Role role = new Role(roleID, roleName, _permissionList);
            roleMap.put(roleID, role);
        }
        rs.close();
        stat.close();
        con.close();
        return roleMap;
    }
    // select_services
    public static Map<String, Service> select_services() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectService = "SELECT * FROM Service;";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectService);
        Map<String, Service> serviceMap = new HashMap<>();
        while (rs.next()) {
            String serviceID = rs.getString("serviceID");
            String serviceName = rs.getString("serviceName");
            String roleList = rs.getString("roleList");
            String permissionList = rs.getString("permissionList");
            String price = rs.getString("price");

            HashSet<Role> _roles = new HashSet<>();
            HashSet<Permission> _permissions = new HashSet<>();
            if (!permissionList.equals("")) {
                String[] _permissionList = permissionList.split(";");
                for (String permission : _permissionList) {
                    _permissions.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!roleList.equals("")) {
                String[] _roleList = roleList.split(";");
                for (String role : _roleList) {
                    _roles.add(GlobalRole.getRoleByID(role));
                }
            }
            Service service = new Service(serviceID, serviceName, _permissions, _roles, price);
            serviceMap.put(serviceID, service);
        }
        rs.close();
        stat.close();
        con.close();
        return serviceMap;
    }
    // (DONE) select user basic information for Log in
    public static LogUser select_userBasic(String userID) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectUser = "SELECT * FROM UserBasic WHERE userID = '" + userID + "';";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectUser);
        if (rs.next()) {
            String name = rs.getString("name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String lastPSWChange = rs.getString("lastPSWChange");
            LogUser user = new LogUser(name, userID, password, email, phone, lastPSWChange);
            rs.close();
            stat.close();
            con.close();
            return user;
        } else {
            return null;
        }
    }
    // select platform administrator by ID
    public static PlatformAdministrator select_platformAdministrator_byID(String platformAdministratorID) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectPlatformAdministrator = "SELECT * FROM PlatformAdministrator WHERE userID = '" + platformAdministratorID + "';";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectPlatformAdministrator);
        PlatformAdministrator platformAdministrator = null;
        if (rs.next()) {
            String userID = rs.getString("userID");
            String name = rs.getString("name");
            String permissionList = rs.getString("permissionList");
            String roleList = rs.getString("roleList");
            String permissionPool = rs.getString("permissionPool");
            String rolePool = rs.getString("rolePool");
            String servicePool = rs.getString("servicePool");
            HashSet<Permission> _permissionList = new HashSet<>();
            HashSet<Role> _roleList = new HashSet<>();
            HashSet<Permission> _permissionPool = new HashSet<>();
            HashSet<Role> _rolePool = new HashSet<>();
            HashSet<Service> _servicePool = new HashSet<>();
            if (!permissionList.equals("")) {
                String[] permissions = permissionList.split(";");
                for (String permission : permissions) {
                    _permissionList.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!roleList.equals("")) {
                String[] roles = roleList.split(";");
                for (String role : roles) {
                    _roleList.add(GlobalRole.getRoleByID(role));
                }
            }
            if (!permissionPool.equals("")) {
                String[] permissions = permissionPool.split(";");
                for (String permission : permissions) {
                    _permissionPool.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!rolePool.equals("")) {
                String[] roles = rolePool.split(";");
                for (String role : roles) {
                    _rolePool.add(GlobalRole.getRoleByID(role));
                }
            }
            if (!servicePool.equals("")) {
                String[] services = servicePool.split(";");
                for (String service : services) {
                    _servicePool.add(GlobalService.getServiceByID(service));
                }
            }
            platformAdministrator = new PlatformAdministrator(name, userID, _permissionPool, _rolePool, _servicePool, _permissionList, _roleList);
        }
        rs.close();
        stat.close();
        con.close();
        return platformAdministrator;
    }
    // select tenant by ID
    public static Tenant select_tenant_byID(String tenantID) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectTenant = "SELECT * FROM Tenant WHERE userID = '" + tenantID + "';";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectTenant);
        Tenant tenant = null;
        if (rs.next()) {
            String userID = rs.getString("userID");
            String name = rs.getString("name");
            String permissionList = rs.getString("permissionList");
            String roleList = rs.getString("roleList");
            String serviceList = rs.getString("serviceList");
            String permissionPool = rs.getString("permissionPool");
            String rolePool = rs.getString("rolePool");
            String tenantAdministratorList = rs.getString("tenantAdministratorList");
            String teantUserList = rs.getString("tenantUserList");
            HashSet<Permission> _permissionList = new HashSet<>();
            HashSet<Role> _roleList = new HashSet<>();
            HashSet<Service> _serviceList = new HashSet<>();
            HashSet<Permission> _permissionPool = new HashSet<>();
            HashSet<Role> _rolePool = new HashSet<>();
            HashSet<String> _tenantAdministratorList = new HashSet<>();
            HashSet<String> _tenantUserList = new HashSet<>();
            if (!permissionList.equals("")) {
                String[] permissions = permissionList.split(";");
                for (String permission : permissions) {
                    _permissionList.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!roleList.equals("")) {
                String[] roles = roleList.split(";");
                for (String role : roles) {
                    _roleList.add(GlobalRole.getRoleByID(role));
                }
            }
            if (!serviceList.equals("")) {
                String[] services = serviceList.split(";");
                for (String service : services) {
                    _serviceList.add(GlobalService.getServiceByID(service));
                }
            }
            if (!permissionPool.equals("")) {
                String[] permissions = permissionPool.split(";");
                for (String permission : permissions) {
                    _permissionPool.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!rolePool.equals("")) {
                String[] roles = rolePool.split(";");
                for (String role : roles) {
                    _rolePool.add(GlobalRole.getRoleByID(role));
                }
            }
            if (!tenantAdministratorList.equals("")) {
                String[] tenantAdministrators = tenantAdministratorList.split(";");
                for (String tenantAdministrator : tenantAdministrators) {
                    _tenantAdministratorList.add(tenantAdministrator);
                }
            }
            if (!teantUserList.equals("")) {
                String[] tenantUsers = teantUserList.split(";");
                for (String tenantUser : tenantUsers) {
                    _tenantUserList.add(tenantUser);
                }
            }

            tenant = new Tenant(name, userID, _serviceList, _tenantAdministratorList, _tenantUserList, _permissionList, _roleList, _permissionPool, _rolePool);
        }
        rs.close();
        stat.close();
        con.close();
        return tenant;
    }
    // select tenantAdministrator by ID
    public static TenantAdministrator select_tenantAdministrator_byID(String tenantAdministratorID) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectTenantAdministrator = "SELECT * FROM TenantAdministrator WHERE userID = '" + tenantAdministratorID + "';";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectTenantAdministrator);
        TenantAdministrator tenantAdministrator = null;
        if (rs.next()) {
            String userID = rs.getString("userID");
            String name = rs.getString("name");
            String permissionList = rs.getString("permissionList");
            String roleList = rs.getString("roleList");
            String permissionPool = rs.getString("permissionPool");
            String rolePool = rs.getString("rolePool");
            String tenantID = rs.getString("tenantID");
            HashSet<Permission> _permissionList = new HashSet<>();
            HashSet<Role> _roleList = new HashSet<>();
            HashSet<Permission> _permissionPool = new HashSet<>();
            HashSet<Role> _rolePool = new HashSet<>();
            if (!permissionList.equals("")) {
                String[] permissions = permissionList.split(";");
                for (String permission : permissions) {
                    _permissionList.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!roleList.equals("")) {
                String[] roles = roleList.split(";");
                for (String role : roles) {
                    _roleList.add(GlobalRole.getRoleByID(role));
                }
            }
            if (!permissionPool.equals("")) {
                String[] permissions = permissionPool.split(";");
                for (String permission : permissions) {
                    _permissionPool.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!rolePool.equals("")) {
                String[] roles = rolePool.split(";");
                for (String role : roles) {
                    _rolePool.add(GlobalRole.getRoleByID(role));
                }
            }
            tenantAdministrator = new TenantAdministrator(name, userID, _permissionList, _roleList, _permissionPool, _rolePool, tenantID);
        }
        rs.close();
        stat.close();
        con.close();
        return tenantAdministrator;
    }
    // select tenantUser by ID
    public static TenantUser select_tenantUser_byID(String tenantUserID) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String selectTenantUser = "SELECT * FROM TenantUser WHERE userID = '" + tenantUserID + "';";
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery(selectTenantUser);
        TenantUser tenantUser = null;
        if (rs.next()) {
            String userID = rs.getString("userID");
            String name = rs.getString("name");
            String permissionList = rs.getString("permissionList");
            String roleList = rs.getString("roleList");
            String permissionPool = rs.getString("permissionPool");
            String rolePool = rs.getString("rolePool");
            String tenantID = rs.getString("tenantID");
            HashSet<Permission> _permissionList = new HashSet<>();
            HashSet<Role> _roleList = new HashSet<>();
            HashSet<Permission> _permissionPool = new HashSet<>();
            HashSet<Role> _rolePool = new HashSet<>();
            if (!permissionList.equals("")) {
                String[] permissions = permissionList.split(";");
                for (String permission : permissions) {
                    _permissionList.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!roleList.equals("")) {
                String[] roles = roleList.split(";");
                for (String role : roles) {
                    _roleList.add(GlobalRole.getRoleByID(role));
                }
            }
            if (!permissionPool.equals("")) {
                String[] permissions = permissionPool.split(";");
                for (String permission : permissions) {
                    _permissionPool.add(GlobalPermission.getPermissionByID(permission));
                }
            }
            if (!rolePool.equals("")) {
                String[] roles = rolePool.split(";");
                for (String role : roles) {
                    _rolePool.add(GlobalRole.getRoleByID(role));
                }
            }
            tenantUser = new TenantUser(name, userID, _permissionList, _roleList, _permissionPool, _rolePool, tenantID);
        }
        rs.close();
        stat.close();
        con.close();
        return tenantUser;
    }
    // select user by ID
    public static User select_user_byID(String userID) throws Exception {
        // go through all user tables (PlatformAdminisatrator, TenantAdministrator, TenantUser and Tenant) to find the user
        User user = null;
        user = select_platformAdministrator_byID(userID);
        if (user == null) {
            user = select_tenantAdministrator_byID(userID);
        }
        if (user == null) {
            user = select_tenantUser_byID(userID);
        }
        if (user == null) {
            user = select_tenant_byID(userID);
        }
        return user;
    }
    // ---------------------------------------------End of The Section------------------------------------------------//


    // -----------------------------------Methods to update the database----------------------------------------------//
    public static void update_platformAdministrator_rolePool(PlatformAdministrator platformAdministrator) throws Exception {
        // change the RolePool into a string
        StringBuilder rolePool = new StringBuilder();
        for (Role role : platformAdministrator.getRolePool()) {
            if (role != null) {
                rolePool.append(role.getRoleID()).append(";");
            }
        }
        // update the database
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String updatePlatformAdministrator = "UPDATE PlatformAdministrator SET rolePool = '" + rolePool + "' WHERE userID = '" + platformAdministrator.getUserID() + "';";
        Statement stat = con.createStatement();
        stat.executeUpdate(updatePlatformAdministrator);
        stat.close();
        con.close();
    }
    public static void update_tenant_roleList(Tenant tenant) throws Exception {
        // change the RoleList into a string
        StringBuilder roleList = new StringBuilder();
        for (Role role : tenant.getRoleList()) {
            if (role != null) {
                roleList.append(role.getRoleID()).append(";");
            }
        }
        // update the database
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String updateTenant = "UPDATE Tenant SET roleList = '" + roleList + "' WHERE userID = '" + tenant.getUserID() + "';";
        Statement stat = con.createStatement();
        stat.executeUpdate(updateTenant);
        stat.close();
        con.close();
    }
    public static void update_userBasic_PSW(String userID, String new_PSW, String lastPSWChange) throws Exception {

    }
    // ---------------------------------------------End of The Section------------------------------------------------//


    // ---------------------------------Methods to delete data of the table-------------------------------------------//
    public static void delete_role_from_Role(String roleID) throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        String deleteRole = "DELETE FROM Role WHERE roleID = '" + roleID + "';";
        Statement stat = con.createStatement();
        stat.executeUpdate(deleteRole);
        stat.close();
        con.close();
    }
    // ---------------------------------------------End of The Section------------------------------------------------//



    // -------------------------------------------------------------------------------------------------------------//
    public static void create() throws Exception {
        drop_tables();
        create_PlatformAdministrator_table();
        create_Tenant_table();
        create_TenantAdministrator_table();
        create_TenantUser_table();
        create_FunctionalPermission_table();
        create_DataPermission_table();
        create_Role_table();
        create_Service_table();
        create_UserBasic_table();
    }
    public static void restore() throws Exception {
        // Insert the data into the database
        // Insert Functional Permissions into the database
        for (int i = 0; i < 100; i ++) {
            HashSet<String> testFunctionIDList = new HashSet<>();
            // Generate a random number between 1 and 20
            int functionNumber = (int) (Math.random() * 20);
            for (int j = 0; j < functionNumber; j ++) {
                // generate a random functionID
                int functionID = (int) (Math.random() * 100);
                testFunctionIDList.add("func" + Integer.toString(functionID));
            }
            // Generate a random PermissionLevel
            int permissionLevel = (int) (Math.random() * 3);
            // Transform the permissionLevel into PermissionLevel
            PermissionLevel testPermissionLevel = PermissionLevel.values()[permissionLevel];
            FunctionalPermission testFunctionalPermission = new FunctionalPermission("FUNC_PER" + Integer.toString(i), "func_permission" + Integer.toString(i), testFunctionIDList, testPermissionLevel);
            insert_FunctionalPermission(testFunctionalPermission, false);
        }

        // Insert Data Permissions into the database
        for (int i = 0; i < 100; i ++) {
            HashSet<DataOperation> testDataOperationList = new HashSet<>();
            // Generate several data operations without duplication
            int dataOperationNumber = (int) (Math.random() * 5);
            for (int j = 0; j < dataOperationNumber; j ++) {
                // generate a random data operation whose index ranges from 0 to 3 (including both ends)
                int dataOperation = (int) (Math.random() * 4);
                testDataOperationList.add(DataOperation.values()[dataOperation]);
            }
            DataPermission testDataPermission = new DataPermission("DATA_PER" + Integer.toString(i), "data_permission" + Integer.toString(i), "data_location" + Integer.toString(i), testDataOperationList);
            insert_DataPermission(testDataPermission, false);
        }

        // Initialize the GlobalPermission
        GlobalPermission.Init(false);

        // Insert Roles into the database
        for (int i = 0; i < 50; i ++) {
            // Generate a random number between 1 and 20
            int permissionNumber = (int) (Math.random() * 20 + 1);
            HashSet<Permission> testPermissionList = new HashSet<>();
            for (int j = 0; j < permissionNumber; j ++) {
                // Generate a random number between 0 and 1
                int permissionType = (int) (Math.random() * 2);
                if (permissionType == 0) {
                    // Generate a random functional permission
                    int functionalPermissionID = (int) (Math.random() * 10);
                    testPermissionList.add(GlobalPermission.getPermissionByID("FUNC_PER" + Integer.toString(functionalPermissionID)));
                } else {
                    // Generate a random data permission
                    int dataPermissionID = (int) (Math.random() * 10);
                    testPermissionList.add(GlobalPermission.getPermissionByID("DATA_PER" + Integer.toString(dataPermissionID)));
                }
            }
            Role testRole = new Role("ROLE" + Integer.toString(i), "role" + Integer.toString(i), testPermissionList);
            insert_Role(testRole, false);
        }

        // Initialize the GlobalRole
        GlobalRole.Init(false);

        // Insert Services into the database
        for (int i = 0; i < 50; i ++) {
            // Generate a random number between 1 and 20
            int roleNumber = (int) (Math.random() * 20 + 1);
            HashSet<Role> testRoleList = new HashSet<>();
            for (int j = 0; j < roleNumber; j ++) {
                // Generate a random role
                int roleID = (int) (Math.random() * 10);
                testRoleList.add(GlobalRole.getGlobalRoleMap().get("ROLE" + Integer.toString(roleID)));
            }
            // Generate a random number for permissions
            int permissionNumber = (int) (Math.random() * 20 + 1);
            HashSet<Permission> testPermissionList = new HashSet<>();
            for (int j = 0; j < permissionNumber; j ++) {
                // Generate a random number between 0 and 1
                int permissionType = (int) (Math.random() * 2);
                if (permissionType == 0) {
                    // Generate a random functional permission
                    int functionalPermissionID = (int) (Math.random() * 10);
                    testPermissionList.add(GlobalPermission.getPermissionByID("FUNC_PER" + Integer.toString(functionalPermissionID)));
                } else {
                    // Generate a random data permission
                    int dataPermissionID = (int) (Math.random() * 10);
                    testPermissionList.add(GlobalPermission.getPermissionByID("DATA_PER" + Integer.toString(dataPermissionID)));
                }
            }
            // Generate a random price
            int price = (int) (Math.random() * 100);

            Service testService = new Service("SERVICE" + Integer.toString(i), "service" + Integer.toString(i), testPermissionList, testRoleList, Integer.toString(price));
            insert_Service(testService, false);
        }

        // Initialize the GlobalService
        GlobalService.Init(false);

        // Insert PlatformAdministrator into the database
        // Generate 2 platformAdministrators, with the first one contains first 50 permissions in his permissionPool, first 25 Roles in his rolePool, first 25 Services in his servicePool
        // and the second one contains the last 50 permissions in his permissionPool, last 25 Roles in his rolePool, last 25 Services in his servicePool
        for (int i = 0; i < 2; i ++) {
            HashSet<Permission> testPermissionPool = new HashSet<>();
            HashSet<Permission> testPermissionList = new HashSet<>();
            HashSet<Role> testRolePool = new HashSet<>();
            HashSet<Role> testRoleList = new HashSet<>();
            HashSet<Service> testServicePool = new HashSet<>();
            for (int j = 0; j < 10; j ++) {
                testPermissionPool.add(GlobalPermission.getPermissionByID("FUNC_PER" + Integer.toString(j + i * 50)));
                testPermissionPool.add(GlobalPermission.getPermissionByID("DATA_PER" + Integer.toString(j + i * 50)));
                testPermissionList.add(GlobalPermission.getPermissionByID("FUNC_PER" + Integer.toString(j + i * 50)));
                testPermissionList.add(GlobalPermission.getPermissionByID("DATA_PER" + Integer.toString(j + i * 50)));
            }
            for (int j = 0; j < 15; j ++) {
                testRolePool.add(GlobalRole.getGlobalRoleMap().get("ROLE" + Integer.toString(j + i * 25)));
                testRoleList.add(GlobalRole.getGlobalRoleMap().get("ROLE" + Integer.toString(j + i * 25)));
            }
            for (int j = 0; j < 15; j ++) {
                testServicePool.add(GlobalService.getServiceByID("SERVICE" + Integer.toString(j + i * 25)));
            }
            PlatformAdministrator testPlatformAdministrator = new PlatformAdministrator("platformAdministrator" + Integer.toString(i), "PLATFORM_ADMIN" + Integer.toString(i), testPermissionPool, testRolePool, testServicePool, testPermissionList, testRoleList);
            insert_PlatformAdministrator(testPlatformAdministrator, false);
        }

        // Insert some Tenants into the database, note that the tenants' permissionPool/List, rolePool/List, serviceList, tenantAdministratorList, tenantUserList are all empty
        for (int i = 0; i < 10; i ++) {
            Tenant testTenant = new Tenant("tenant" + Integer.toString(i), "TENANT" + Integer.toString(i), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
            insert_Tenant(testTenant, false);
        }

//        // Insert some TenantAdministrators into the database, note that the tenantAdministrators' permissionPool/List, rolePool/List are all empty
//        for (int i = 0; i < 10; i ++) {
//            TenantAdministrator testTenantAdministrator = new TenantAdministrator("TENANT_ADMIN" + Integer.toString(i), "tenantAdministrator" + Integer.toString(i), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
//            insert_TenantAdministrator(testTenantAdministrator, "TENANT" + Integer.toString(i % 10), false);
//        }
//
//        // Insert some TenantUsers into the database, note that the tenantUsers' permissionPool/List, rolePool/List are all empty
//        for (int i = 0; i < 30; i ++) {
//            TenantUser testTenantUser = new TenantUser("TENANT_USER" + Integer.toString(i), "tenantUser" + Integer.toString(i), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
//            insert_TenantUser(testTenantUser, "TENANT" + Integer.toString(i % 10),false);
//        }
    }
    public static void Init() throws Exception {
        // Initialize the GlobalPermission
        GlobalPermission.Init(false);

        // Initialize the GlobalRole
        GlobalRole.Init(false);

        // Initialize the GlobalService
        GlobalService.Init(false);
    }
    public static void main(String[] args) throws Exception {
        create();
        restore();
    }
    // ---------------------------------------------End of The Section------------------------------------------------//
}