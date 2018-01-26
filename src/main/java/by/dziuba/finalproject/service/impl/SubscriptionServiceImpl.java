package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.SubscriptionDAOImpl;
import by.dziuba.subscription.entity.Subscription;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class SubscriptionServiceImpl {
    private static final SubscriptionDAOImpl subscriptionDao = new SubscriptionDAOImpl();

    public boolean addSubscription(List<Subscription> subscriptions) throws ServiceException {
        try {
            return subscriptionDao.insertSubscription(subscriptions);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
