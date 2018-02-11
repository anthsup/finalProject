package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.SubscriptionDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOImpl implements SubscriptionDAO {
    private static final String INSERT_SUBSCRIPTION = "INSERT INTO subscription" +
            "(user_id, periodical_id, start_date, end_date, price) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM subscription WHERE user_id = ?";

    @Override
    public void insertSubscription(List<Subscription> subscriptions) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_SUBSCRIPTION)) {
            for (Subscription subscription : subscriptions) {
                statement.setInt(1, subscription.getUserId());
                statement.setInt(2, subscription.getPeriodicalId());
                statement.setDate(3, subscription.getStartDate());
                statement.setDate(4, subscription.getEndDate());
                statement.setBigDecimal(5, subscription.getPrice());
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Subscription> findSubscriptionByUserId(int userId) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_USER_ID)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    subscriptions.add(createSubscription(resultSet));
                }
            }
            return subscriptions;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    private Subscription createSubscription(ResultSet resultSet) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setUserId(resultSet.getInt("user_id"));
        subscription.setPeriodicalId(resultSet.getInt("periodical_id"));
        subscription.setStartDate(resultSet.getDate("start_date"));
        subscription.setEndDate(resultSet.getDate("end_date"));
        subscription.setPrice(resultSet.getBigDecimal("price"));
        return subscription;
    }
}
