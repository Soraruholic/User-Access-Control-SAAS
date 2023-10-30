package SAAS.UAC.UPR;

import java.util.Map;
import SAAS.Database.Database;

public class GlobalService {
    protected static Map<String, Service> globalServiceMap;
    public static void Init(boolean verbose) throws Exception {
        globalServiceMap = Database.select_services();
        if (verbose) {
            printAllServices();
        }
    }

    public static Service getServiceByID(String serviceID) throws Exception {
        if (globalServiceMap.containsKey(serviceID)) {
            return globalServiceMap.get(serviceID);
        } else {
            return null;
        }
    }

    // For DEBUG:
    // Print all the services in the global service map
    private static void printAllServices() {
        System.out.println("Global Service Map:");
        for (Map.Entry<String, Service> entry : globalServiceMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getServiceName());
        }
    }

}
