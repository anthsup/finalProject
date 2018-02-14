package by.dziuba.subscription.listener;

import by.dziuba.subscription.database.DBConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fires after application context is initialized and initializes Connection pool.
 * Destroys it after application context is destroyed.
 * @see DBConnectionPool
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        DBConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        DBConnectionPool.getInstance().close();
    }
}
