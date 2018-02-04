package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDAOImpl {
    private static final String SELECT_ALL = "SELECT * FROM periodical_genre";
    private static final String DELETE_BY_ID = "DELETE FROM periodical_genre WHERE periodical_id = ?";

    public Map<Integer, List<Genre>> findAll() throws DAOException {
        Map<Integer, List<Genre>> genresMap = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int genreId = resultSet.getInt("periodical_id");
                    if (genresMap.get(genreId) != null) {
                        genresMap.get(genreId).add(createGenre(resultSet));
                    } else {
                        List<Genre> genres = new ArrayList<>();
                        genres.add(createGenre(resultSet));
                        genresMap.put(genreId, genres);
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return genresMap;
    }

    public void deleteById(int periodicalId) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, periodicalId);
            statement.execute();
        } catch (SQLException | DBException e) {
            throw new DAOException(e);
        }
    }

    private Genre createGenre(ResultSet selected) throws SQLException {
        Genre genre = new Genre();
        genre.setPeriodicalId(selected.getInt("periodical_id"));
        genre.setName(selected.getString("genre_name"));
        return genre;
    }
}
