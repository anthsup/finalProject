package by.dziuba.subscription.database;

import by.dziuba.subscription.database.util.DBResourceManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class DBConnectionPoolTest {
    private static final int TIMEOUT = 9;

    private DBConnectionPool pool;
    private List<DBConnectionPool.PoolConnection> poolConnections;

    @BeforeMethod
    public void setUp() throws Exception {
        pool = DBConnectionPool.getInstance();
        poolConnections = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(DBResourceManager.getProperty(DBResourceManager.POOL_INITIAL_SIZE)); i++) {
            poolConnections.add(pool.getConnection());
        }
    }

    @Test
    public void testGetConnection() throws Exception {
        for (DBConnectionPool.PoolConnection conn : poolConnections) {
            assertTrue(conn.getConnection().isValid(TIMEOUT));
        }
    }

    @AfterMethod
    public void tearDown() {
        pool.close();
    }
}