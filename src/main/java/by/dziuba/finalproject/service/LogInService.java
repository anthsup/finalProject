package by.dziuba.finalproject.service;


import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.exception.ServiceException;

public interface LogInService {
    User logIn(String login, String password) throws ServiceException;

    String encodePassword(String password);
}
