package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.SubscriptionDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.SubscriptionDAOImpl;
import by.dziuba.subscription.entity.Subscription;
import by.dziuba.subscription.service.SubscriptionService;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    private static final SubscriptionDAO subscriptionDao = new SubscriptionDAOImpl();

    @Override
    public void addSubscription(List<Subscription> subscriptions) throws ServiceException {
        try {
            subscriptionDao.insertSubscription(subscriptions);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Subscription> getSubscriptionByUserId(int userId) throws ServiceException {
        try {
            return subscriptionDao.findSubscriptionByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
