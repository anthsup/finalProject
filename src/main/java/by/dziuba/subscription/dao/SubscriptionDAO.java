package by.dziuba.subscription.dao;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.entity.Subscription;

import java.util.List;

public interface SubscriptionDAO {
    void insertSubscription(List<Subscription> subscriptions) throws DAOException;

    List<Subscription> findSubscriptionByUserId(int userId) throws DAOException;
}
