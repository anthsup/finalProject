package by.dziuba.finalproject.dao.impl;

import by.dziuba.finalproject.dao.exception.DAOException;
import by.dziuba.finalproject.database.DBConnectionPool;
import by.dziuba.finalproject.database.exception.DBException;
import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.dao.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String INSERT_USER =
            "INSERT INTO user (login, email, firstName, lastName, password) VALUES (?,?,?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ? ";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ? ";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private static final String UPDATE_STATUS = "UPDATE user SET isBanned = ? WHERE id = ?";
    private static final String UPDATE_ROLE = "UPDATE user SET isAdmin = ? WHERE id = ?";

    @Override
    public User findUserByLogin(String login) throws DAOException {
        return findUserBy(login, SELECT_USER_BY_LOGIN);
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean executed = false;
        try (DBConnectionPool.PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getPassword());
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }

    private User findUserBy(String parameter, String query) throws DAOException {
        User user = null;
        try (DBConnectionPool.PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(query)) {
            statement.setString(1, parameter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setPassword(resultSet.getString("password"));
        user.setLogin(resultSet.getString("login"));
        user.setBanned(resultSet.getBoolean("isBanned"));
        user.setAdmin(resultSet.getBoolean("isAdmin"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        user.setRegistrationDate(resultSet.getDate("registration_date"));
        return user;
    }

    @Override
    public User findUserById(int id) throws DAOException {
        User user = null;
        try (DBConnectionPool.PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(SELECT_USER_BY_ID);) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws DAOException {
        return findUserBy(email, SELECT_USER_BY_EMAIL);
    }

    @Override
    public boolean updateUserStatus(int id, User user) throws DAOException {
        boolean executed = false;
        try (DBConnectionPool.PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(UPDATE_STATUS);) {
            statement.setString(1, user.isBanned());
            statement.setInt(2, id);
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }

    @Override
    public boolean updateUserPassword(int id, String password) throws DAOException {
        boolean executed = false;
        try (DBConnectionPool.PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(UPDATE_PASSWORD);) {
            statement.setString(1, password);
            statement.setInt(2, id);
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }
}
