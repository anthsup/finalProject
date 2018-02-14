package by.dziuba.subscription.database;


import by.dziuba.subscription.exception.DBException;
import by.dziuba.subscription.database.util.DBConnector;
import by.dziuba.subscription.database.util.DBResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Contains and manages database connections.
 */
public class DBConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(DBConnectionPool.class);

    private static final int POOL_CAPACITY = Integer.parseInt(DBResourceManager
            .getProperty(DBResourceManager.POOL_CAPACITY));
    private static final int POOL_INITIAL_SIZE = Integer.parseInt(DBResourceManager
            .getProperty(DBResourceManager.POOL_INITIAL_SIZE));
    private static final int TIMEOUT_SECONDS = Integer.parseInt(DBResourceManager
            .getProperty(DBResourceManager.POOL_TIMEOUT));

    private BlockingQueue<Connection> pool;
    private AtomicInteger connectionCount;
    private Lock lock;

    private DBConnectionPool() {
        try {
            pool = new ArrayBlockingQueue<>(POOL_CAPACITY);
            connectionCount = new AtomicInteger();
            lock = new ReentrantLock();
            for (int i = 0; i < POOL_INITIAL_SIZE; i++) {
                pool.offer(DBConnector.getConnection());
                connectionCount.incrementAndGet();
            }
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public static DBConnectionPool getInstance() {
        return DBConnectionPoolHolder.INSTANCE;
    }

    /**
     * Takes connection from the queue, if there still are connections.
     * In other case database manager creates it. If pool capacity is exceeded,
     * DBException is thrown.
     *
     * @return PoolConnection — wrapped autoclosable Connection
     */
    public PoolConnection getConnection() throws DBException {
        Connection connection;
        try {
            connection = pool.poll(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            lock.lock();
            if (connection == null && connectionCount.get() < POOL_CAPACITY) {
                connection = DBConnector.getConnection();
                pool.offer(connection);
                connectionCount.incrementAndGet();
            }

            if (connection == null) {
                throw new DBException("Too many connections.");
            }
        } catch (InterruptedException | SQLException e) {
            throw new DBException(e);
        } finally {
            lock.unlock();
        }
        return new PoolConnection(connection);
    }

    /**
     * Closes the pool by closing each of its connections.
     */
    public void close() {
        pool.forEach(this::closeConnection);
    }

    /**
     * Closes unwrapped connections.
     * @param connection to be closed
     */
    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    private static class DBConnectionPoolHolder {
        private static final DBConnectionPool INSTANCE = new DBConnectionPool();
    }

    /**
     * Wrapper class to use proxy connection as a normal connection.
     * Implements AutoCloseable interface to be used in try-with-resources block.
     */
    public class PoolConnection implements AutoCloseable {
        private Connection connection;

        private PoolConnection(Connection connection) {
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }

        /**
         * Removes connection from the queue if it is already closed.
         * In other cases returns it to the queue.
         */
        @Override
        public void close() {
            try {
                if (!connection.isClosed()) {
                    pool.offer(connection);
                } else {
                    pool.remove(connection);
                }
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }
}