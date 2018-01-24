package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthorDAOImpl {
    private static final String SELECT_ALL = "SELECT * FROM author";

    public Map<Integer, Author> findAll() throws DAOException {
        Map<Integer, Author> authorsMap = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    authorsMap.put(resultSet.getInt("id"), createAuthor(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return authorsMap;
    }

    private Author createAuthor(ResultSet selected) throws SQLException {
        Author author = new Author();
        author.setId(selected.getInt("id"));
        author.setFullName(selected.getString("fullName"));
        return author;
    }
}
