package by.dziuba.finalproject.service;

import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.exception.ServiceException;

public interface UserService {
    User getUserByLogin(String login) throws ServiceException;
    User getUserById(int id) throws ServiceException;
    boolean addUser(User user) throws ServiceException;
    boolean updateUserStatus(int id, User user) throws ServiceException;
    void updateUserRole(int id, User user) throws ServiceException;
    User getUserByEmail(String email) throws ServiceException;
}
