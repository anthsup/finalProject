package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.exception.ServiceException;

import java.sql.Timestamp;
import java.util.List;

public interface UserService {
    User getUserByLogin(String login) throws ServiceException;
    boolean addUser(User user) throws ServiceException;
    boolean changeUserBanStatus(int userId, boolean banStatus) throws ServiceException;
    User getUserById(int id) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
    boolean updateUserPassword(int id, String password) throws ServiceException;
    boolean updateUserById(User user) throws ServiceException;
    void updateLoan(User user) throws ServiceException;
}
