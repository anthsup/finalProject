package by.dziuba.finalproject.dao;

import by.dziuba.finalproject.dao.exception.DAOException;
import by.dziuba.finalproject.entity.User;

public interface UserDAO {
    User findUserByLogin(String login) throws DAOException;
    boolean addUser(User user) throws DAOException;
    User findUserById(int id) throws DAOException;
    User findUserByEmail(String email) throws DAOException;
    boolean updateUserStatus(int id, User user) throws DAOException;
    boolean updateUserPassword(int id, String password) throws DAOException;
}
