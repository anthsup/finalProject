package by.dziuba.finalproject.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnector {
    private static final String URL = DBResourceManager.getProperty(DBResourceManager.URL);
    private static final String USER = DBResourceManager.getProperty(DBResourceManager.USER);
    private static final String PASSWORD = DBResourceManager.getProperty(DBResourceManager.PASSWORD);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
