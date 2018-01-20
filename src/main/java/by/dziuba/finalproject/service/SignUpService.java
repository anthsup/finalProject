package by.dziuba.finalproject.service;

import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.exception.ServiceException;

public interface SignUpService {
    boolean signUp(User user) throws ServiceException;
}
