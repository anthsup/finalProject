package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.PeriodicalDAO;
import by.dziuba.subscription.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.exception.DBException;
import by.dziuba.subscription.entity.Periodical;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class PeriodicalDAOImpl implements PeriodicalDAO {
    private static final String SELECT_ALL = "SELECT * FROM periodical";
    private static final String SELECT_BY_ID = "SELECT * FROM periodical WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM periodical WHERE id = ?";
    private static final String UPDATE_BY_ID = "UPDATE periodical SET title = ?, price = ?," +
            "periodicity = ?, author_id = ?, periodical_type_id = ?, coverImage = ?," +
            "description = ?, booksAmount = ? WHERE id = ?";
    private static final String SELECT_BY_AUTHOR_ID = "SELECT * FROM periodical WHERE author_id = ?";
    private static final String INSERT_PERIODICAL = "INSERT INTO periodical (title, price, periodicity, author_id," +
            "periodical_type_id, coverImage, description, booksAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_TITLE = "SELECT * FROM periodical WHERE title = ?";
    private static final String SELECT_BY_PERIODICAL_TYPE = "SELECT * FROM periodical WHERE periodical_type_id = ?";
    private static final String SELECT_BY_PERIODICITY = "SELECT * FROM periodical WHERE periodicity = ?";

    @Override
    public List<Periodical> findAll(int pageNumber, int periodicalsPerPage) throws DAOException {
        String selectQuery = SELECT_ALL + " LIMIT " + (pageNumber - 1) * periodicalsPerPage + ", " + periodicalsPerPage;
        return findAll(selectQuery);
    }

    @Override
    public int getPeriodicalsNumber() throws DAOException {
        return findAll(SELECT_ALL).size();
    }

    @Override
    public Periodical findByPeriodicalId(int id) throws DAOException {
        Periodical periodical = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodical = createPeriodical(resultSet);
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodical;
    }

    @Override
    public void deleteById(int periodicalId) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, periodicalId);
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateById(Periodical periodical) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            setBookSeriesData(statement, periodical);
            statement.setInt(9, periodical.getId());
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void insertPeriodical(Periodical periodical) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_PERIODICAL)) {
            setBookSeriesData(statement, periodical);
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Periodical> findByAuthorId(int authorId) throws DAOException {
        return findBy(SELECT_BY_AUTHOR_ID, authorId);
    }

    @Override
    public List<Periodical> findByAuthorId(int authorId, int pageNumber, int periodicalsPerPage) throws DAOException {
        return findBy(SELECT_BY_AUTHOR_ID, authorId, pageNumber, periodicalsPerPage);
    }

    @Override
    public Periodical findByTitle(String periodicalTitle) throws DAOException {
        Periodical periodical = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_TITLE)) {
            statement.setString(1, periodicalTitle);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodical = createPeriodical(resultSet);
                }
            }
            return periodical;
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Periodical> findByPeriodicalType(int periodicalTypeId, int pageNumber, int periodicalsPerPage) throws DAOException {
        return findBy(SELECT_BY_PERIODICAL_TYPE, periodicalTypeId, pageNumber, periodicalsPerPage);
    }

    @Override
    public List<Periodical> findByPeriodicity(int periodicity, int pageNumber, int periodicalsPerPage) throws DAOException {
        return findBy(SELECT_BY_PERIODICITY, periodicity, pageNumber, periodicalsPerPage);
    }

    private List<Periodical> findBy(String query, int value) throws DAOException {
        List<Periodical> periodicals = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicals.add(createPeriodical(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicals;
    }

    private List<Periodical> findBy(String query, int value, int pageNumber, int periodicalsPerPage) throws DAOException {
        query = query + " LIMIT " + (pageNumber - 1) * periodicalsPerPage + ", " + periodicalsPerPage;
        return findBy(query, value);
    }

    private void setBookSeriesData(PreparedStatement statement, Periodical periodical) throws SQLException {
        statement.setString(1, periodical.getTitle());
        statement.setBigDecimal(2, periodical.getPrice());
        statement.setInt(3, periodical.getPeriodicity());
        statement.setInt(5, periodical.getTypeId());
        statement.setString(6, periodical.getCoverImage());
        statement.setString(7, periodical.getDescription());
        if (periodical.getAuthorId() == 0) {
            statement.setNull(4, NULL);
            statement.setNull(8, NULL);
        } else {
            statement.setInt(4, periodical.getAuthorId());
            statement.setInt(8, periodical.getBooksAmount());
        }
    }

    private List<Periodical> findAll(String selectQuery) throws DAOException {
        List<Periodical> periodicals = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(selectQuery)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicals.add(createPeriodical(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicals;
    }

    private Periodical createPeriodical(ResultSet resultSet) throws SQLException {
        Periodical periodical = new Periodical();
        periodical.setId(resultSet.getInt("id"));
        periodical.setTitle(resultSet.getString("title"));
        periodical.setPrice(BigDecimal.valueOf(resultSet.getDouble("price")));
        periodical.setPeriodicity(resultSet.getInt("periodicity"));
        periodical.setAuthorId(resultSet.getInt("author_id"));
        periodical.setTypeId(resultSet.getInt("periodical_type_id"));
        periodical.setCoverImage(resultSet.getString("coverImage"));
        periodical.setDescription(resultSet.getString("description"));
        periodical.setBooksAmount(resultSet.getInt("booksAmount"));
        return periodical;
    }
}
