package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.exception.ServiceException;

public interface SignUpService {
    boolean signUp(User user) throws ServiceException;
}
