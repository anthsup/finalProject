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
    private static final String INSERT_USER = "INSERT INTO user (login, email, firstName, lastName, city, address," +
            "postalIndex, registrationDate, password, loan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private static final String UPDATE_BY_ID = "UPDATE user SET login = ?, email = ?, firstName = ?, lastName = ?," +
            "city = ?, address = ?, postalIndex = ?, photo = ? WHERE id = ?";
    private static final String BAN_USER = "UPDATE user SET banned = ? WHERE id = ?";
    private static final String UPDATE_LOAN = "UPDATE user SET loan = ? WHERE id = ?";

    @Override
    public User findUserByLogin(String login) throws DAOException {
        return findUserBy(login, SELECT_BY_LOGIN);
    }
    //TODO refactor return expressions
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
            statement.setString(5, user.getCity());
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getPostalIndex());
            statement.setDate(8, user.getRegistrationDate());
            statement.setString(9, user.getPassword());
            statement.setBigDecimal(10, user.getLoan());
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }

    @Override
    public boolean updateUserPassword(int id, String password) throws DAOException {
        try (PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_PASSWORD)) {
            statement.setString(1, password);
            statement.setInt(2, id);
            statement.executeUpdate();
            return true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean updateUserById(User user) throws DAOException {
        try (PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getCity());
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getPostalIndex());
            statement.setString(8, user.getPhoto());
            statement.setInt(9, user.getId());
            statement.executeUpdate();
            return true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean updateUserBanStatus(int userId, boolean banStatus) throws DAOException {
        boolean executed = false;
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(BAN_USER)) {
            statement.setBoolean(1, banStatus);
            statement.setInt(2, userId);
            statement.executeUpdate();
            executed = true;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return executed;
    }

    @Override
    public void updateLoan(User user) throws DAOException {
        try (PoolConnection connectionFromPool = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.getConnection().prepareStatement(UPDATE_LOAN)) {
            statement.setBigDecimal(1, user.getLoan());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
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

    //todo make create methods default
    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setPassword(resultSet.getString("password"));
        user.setLogin(resultSet.getString("login"));
        user.setBanned(resultSet.getBoolean("banned"));
        user.setAdmin(resultSet.getBoolean("admin"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setPostalIndex(resultSet.getString("postalIndex"));
        user.setAddress(resultSet.getString("address"));
        user.setCity(resultSet.getString("city"));
        user.setEmail(resultSet.getString("email"));
        user.setRegistrationDate(resultSet.getDate("registrationDate"));
        user.setPhoto(resultSet.getString("photo"));
        user.setLoan(resultSet.getBigDecimal("loan"));
        return user;
    }
}
