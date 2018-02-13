package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.GenreDAO;
import by.dziuba.subscription.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.exception.DBException;
import by.dziuba.subscription.entity.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDAOImpl implements GenreDAO {
    private static final String SELECT_ALL_PERIODICAL_GENRES = "SELECT * FROM periodical_genre";
    private static final String DELETE_BY_PERIODICAL_ID = "DELETE FROM periodical_genre WHERE periodical_id = ?";
    private static final String SELECT_BY_PERIODICAL_ID = "SELECT * FROM periodical_genre WHERE periodical_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM genre";
    private static final String INSERT_PERIODICAL_GENRES = "INSERT INTO periodical_genre" +
            "(periodical_id, genre_name) VALUES (?, ?)";
    private static final String INSERT_GENRE = "INSERT into genre (genre_name) VALUES (?)";
    private static final String DELETE_GENRE = "DELETE FROM genre WHERE genre_name = ?";
    private static final String SELECT_BY_GENRE_NAME = "SELECT * FROM periodical_genre WHERE genre_name = ?";

    private static final String GENRE_NAME = "genre_name";
    private static final String PERIODICAL_ID = "periodical_id";

    @Override
    public Map<Integer, List<Genre>> findAllPeriodicalGenres() throws DAOException {
        Map<Integer, List<Genre>> genresMap = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL_PERIODICAL_GENRES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int genreId = resultSet.getInt(PERIODICAL_ID);
                    if (genresMap.get(genreId) != null) {
                        genresMap.get(genreId).add(new Genre(resultSet.getString(GENRE_NAME)));
                    } else {
                        List<Genre> genres = new ArrayList<>();
                        genres.add(new Genre(resultSet.getString(GENRE_NAME)));
                        genresMap.put(genreId, genres);
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return genresMap;
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> genres = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(new Genre(resultSet.getString(GENRE_NAME)));
                }
            }
            return genres;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteByPeriodicalId(int periodicalId) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_PERIODICAL_ID)) {
            statement.setInt(1, periodicalId);
            statement.execute();
        } catch (SQLException | DBException e) {
            throw new DAOException(e);
        }
    }

    @Override
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

    @Override
    public void insertGenre(String genreName) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_GENRE)) {
            statement.setString(1, genreName);
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteGenre(String genreName) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_GENRE)) {
            statement.setString(1, genreName);
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Genre> findByPeriodicalId(int periodicalId) throws DAOException {
        List<Genre> genres = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_PERIODICAL_ID)) {
            statement.setInt(1, periodicalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(new Genre(resultSet.getString(GENRE_NAME)));
                }
            }
            return genres;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Integer> findPeriodicalsByGenreName(String genreName) throws DAOException {
        List<Integer> periodicalIds = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_GENRE_NAME)) {
            statement.setString(1, genreName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicalIds.add(resultSet.getInt(PERIODICAL_ID));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicalIds;
    }
}
