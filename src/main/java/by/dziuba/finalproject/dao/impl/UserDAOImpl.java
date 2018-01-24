package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.UserDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.DBConnectionPool.PoolConnection;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM user";
    private static final String INSERT_USER =
            "INSERT INTO user (login, email, firstName, lastName, password) VALUES (?,?,?,?,?)";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private static final String BAN_USER = "UPDATE user SET isBanned = ? WHERE id = ?";

    @Override
    public User findUserByLogin(String login) throws DAOException {
        return findUserBy(login, SELECT_BY_LOGIN);
    }

    @Override
    public User findUserById(int id) throws DAOException {
        User user = null;
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(SELECT_BY_ID)) {
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
    public List<User> findAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(SELECT_ALL)) {
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public boolean addUser(User user) throws DAOException {
        boolean executed = false;
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
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

    @Override
    public boolean updateUserPassword(int id, String password) throws DAOException {
        boolean executed = false;
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(UPDATE_PASSWORD)) {
            statement.setString(1, password);
            statement.setInt(2, id);
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }

    @Override
    public boolean banUser(int userId) throws DAOException {
        boolean executed = false;
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(BAN_USER)) {
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }

    private User findUserBy(String parameter, String query) throws DAOException {
        User user = null;
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
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
        user.setBanned(resultSet.getBoolean("banned"));
        user.setAdmin(resultSet.getBoolean("admin"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        user.setRegistrationDate(resultSet.getDate("registrationDate"));
        return user;
    }
}
