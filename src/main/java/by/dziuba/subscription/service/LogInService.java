package by.dziuba.subscription.service;


import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.exception.ServiceException;

public interface LogInService {
    User logIn(String login, String password) throws ServiceException;

    String encodePassword(String password);
}
