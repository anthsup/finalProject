package by.dziuba.finalproject.service.impl;

import by.dziuba.finalproject.dao.exception.DAOException;
import by.dziuba.finalproject.dao.impl.UserDAOImpl;
import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.dao.UserDAO;

import java.sql.Timestamp;

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
    public boolean addUser(User user) throws ServiceException {
        try {
            return userDao.addUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        try {
            return userDao.getUserById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            return userDao.getUserByEmail(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserRole(int id, User user) throws ServiceException {
        try {
            userDao.updateUserRole(id,role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean banUser(int userId, Timestamp banExpirationDate) throws ServiceException {
        try {
            return userDao.banUser(userId, banExpirationDate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }
}
