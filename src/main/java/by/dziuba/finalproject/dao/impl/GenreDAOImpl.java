package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDAOImpl {
    private static final String SELECT_ALL_PERIODICAL_GENRES = "SELECT * FROM periodical_genre";
    private static final String DELETE_BY_PERIODICAL_ID = "DELETE FROM periodical_genre WHERE periodical_id = ?";
    private static final String SELECT_BY_PERIODICAL_ID = "SELECT * FROM periodical_genre WHERE periodical_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM genre";
    private static final String INSERT_PERIODICAL_GENRES = "INSERT INTO periodical_genre" +
            "(periodical_id, genre_name) VALUES (?, ?)";

    public Map<Integer, List<Genre>> findAllPeriodicalGenres() throws DAOException {
        Map<Integer, List<Genre>> genresMap = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL_PERIODICAL_GENRES)) {
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

    public List<Genre> findAll() throws DAOException {
        List<Genre> genres = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(createGenre(resultSet));
                }
            }
            return genres;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void deleteByPeriodicalId(int periodicalId) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_PERIODICAL_ID)) {
            statement.setInt(1, periodicalId);
            statement.execute();
        } catch (SQLException | DBException e) {
            throw new DAOException(e);
        }
    }

    public void insertPeriodicalGenres(int periodicalId, List<Genre> periodicalGenres) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_PERIODICAL_GENRES)) {
            for (Genre genre : periodicalGenres) {
               statement.setInt(1, periodicalId);
               statement.setString(2, genre.getName());
               statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Genre> findByPeriodicalId(int periodicalId) throws DAOException {
        List<Genre> genres = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_PERIODICAL_ID)) {
            statement.setInt(1, periodicalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(createGenre(resultSet));
                }
            }
            return genres;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    private Genre createGenre(ResultSet selected) throws SQLException {
        return new Genre(selected.getString("genre_name"));
    }
}
