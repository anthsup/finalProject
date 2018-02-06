package by.dziuba.subscription.dao;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.entity.User;

import java.util.List;

public interface UserDAO {
    User findUserByLogin(String login) throws DAOException;
    User findUserById(int id) throws DAOException;
    List<User> findAllUsers() throws DAOException;
    boolean addUser(User user) throws DAOException;
    boolean updateUserBanStatus(int userId, boolean banStatus) throws DAOException;
    boolean updateUserPassword(int id, String password) throws DAOException;
    boolean updateUserById(User user) throws DAOException;
}
