package by.dziuba.subscription.database.util;

import java.util.ResourceBundle;

public final class DBResourceManager {
    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String POOL_INITIAL_SIZE = "pool.initial.size";
    public static final String POOL_CAPACITY = "pool.capacity";
    public static final String POOL_TIMEOUT = "pool.timeout";

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public static String getProperty(String key) { return resourceBundle.getString(key); }
}
