package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Subscription;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionDAOImpl {
    private static final String INSERT_SUBSCRIPTION = "INSERT INTO subscription" +
            "(user_id, periodical_id, start_date, end_date) VALUES (?, ?, ?, ?)";

    public boolean insertSubscription(List<Subscription> subscriptions) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_SUBSCRIPTION)) {
            for (Subscription subscription : subscriptions) {
                statement.setInt(1, subscription.getUserId());
                statement.setInt(2, subscription.getPeriodicalId());
                statement.setDate(3, subscription.getStartDate());
                statement.setDate(4, subscription.getEndDate());
                statement.executeUpdate();
            }
            return true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

}
