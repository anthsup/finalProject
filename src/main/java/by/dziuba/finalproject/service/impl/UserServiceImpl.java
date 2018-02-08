package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.UserDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.UserDAOImpl;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDAO userDao = new UserDAOImpl();

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            return userDao.findUserByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        try {
            return userDao.findUserById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addUser(User user) throws ServiceException {
        try {
            return userDao.addUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDao.findAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeUserBanStatus(int userId, boolean banStatus) throws ServiceException {
        try {
            return userDao.updateUserBanStatus(userId, banStatus);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUserPassword(int id, String password) throws ServiceException {
        try {
            String hashedPassword = new LogInServiceImpl().encodePassword(password);
            return userDao.updateUserPassword(id, hashedPassword);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUserById(User user) throws ServiceException {
        try {
            return userDao.updateUserById(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void updateLoan(User user) throws ServiceException {
        try {
            userDao.updateLoan(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
