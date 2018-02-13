package by.dziuba.subscription.dao;

import by.dziuba.subscription.exception.DAOException;
import by.dziuba.subscription.entity.User;

import java.util.List;

public interface UserDAO {
    User findUserByLogin(String login) throws DAOException;

    User findUserByEmail(String email) throws DAOException;

    User findUserById(int id) throws DAOException;

    List<User> findAllUsers() throws DAOException;

    void addUser(User user) throws DAOException;

    void updateUserBanStatus(int userId, boolean banStatus) throws DAOException;

    void updateUserPassword(int id, String password) throws DAOException;

    void updateUserById(User user) throws DAOException;

    void updateLoan(User user) throws DAOException;
}
