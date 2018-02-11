package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.Subscription;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public interface SubscriptionService {
    void addSubscription(List<Subscription> subscriptions) throws ServiceException;

    List<Subscription> getSubscriptionByUserId(int userId) throws ServiceException;
}
