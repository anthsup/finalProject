package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Periodicity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PeriodicityDAOImpl {
    private static final String SELECT_ALL = "SELECT * FROM periodicity";

    public Map<Integer, Periodicity> findAll() throws DAOException {
        Map<Integer, Periodicity> periodicityMap = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicityMap.put(resultSet.getInt("id"), createPeriodicity(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicityMap;
    }

    private Periodicity createPeriodicity(ResultSet selected) throws SQLException {
        Periodicity periodicity = new Periodicity();
        periodicity.setId(selected.getInt("id"));
        periodicity.setPeriodicity(selected.getString("periodicity"));
        return periodicity;
    }
}
