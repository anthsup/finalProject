package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDAOImpl {
    private static final String SELECT_ALL = "SELECT * FROM author";
    private static final String SELECT_BY_ID = "SELECT * FROM author WHERE id = ?";
    private static final String INSERT_AUTHOR = "INSERT INTO author (fullName) VALUES (?)";
    private static final String DELETE_BY_ID = "DELETE FROM author WHERE id = ?";

    public List<Author> findAll() throws DAOException {
        List<Author> authorsMap = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    authorsMap.add(createAuthor(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return authorsMap;
    }

    public Author findById(int authorId) throws DAOException {
        Author author = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    author = createAuthor(resultSet);
                }
            }
            return author;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void insertAuthor(String authorName) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_AUTHOR)) {
            statement.setString(1, authorName);
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void deleteById(int authorId) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, authorId);
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    private Author createAuthor(ResultSet selected) throws SQLException {
        Author author = new Author();
        author.setId(selected.getInt("id"));
        author.setFullName(selected.getString("fullName"));
        return author;
    }
}
