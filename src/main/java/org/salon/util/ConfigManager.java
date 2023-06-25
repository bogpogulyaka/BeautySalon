package org.salon.util;

import java.util.ResourceBundle;

public class ConfigManager {
    private static ConfigManager instance;
    private ResourceBundle resource;
    private static final String DB_BUNDLE_NAME = "db";
    public static final String DB_HOST = "db.host";
    public static final String DB_PORT = "db.port";
    public static final String DB_URL = "db.url";
    public static final String DB_TABLE = "db.table";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";

    private static final String LOGGER_BUNDLE_NAME = "log4j";


    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.resource = ResourceBundle.getBundle(DB_BUNDLE_NAME);
        }
        return instance;
    }
    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
