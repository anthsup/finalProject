package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Periodical;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class PeriodicalDAOImpl {
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

    public List<Periodical> findAll(int pageNumber, int periodicalsPerPage) throws DAOException {
        String selectQuery = SELECT_ALL + " LIMIT " + (pageNumber - 1) * periodicalsPerPage + ", " + periodicalsPerPage;
        return findAll(selectQuery);
    }

    public int getPeriodicalsNumber() throws DAOException {
        return findAll(SELECT_ALL).size();
    }

    public Periodical findById(int id) throws DAOException {
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

    public void deleteById(int periodicalId) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, periodicalId);
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void updateById(Periodical periodical) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, periodical.getTitle());
            statement.setBigDecimal(2, periodical.getPrice());
            statement.setInt(3, periodical.getPeriodicity());
            setBookSeriesData(statement, periodical);
            statement.setInt(5, periodical.getTypeId());
            statement.setString(6, periodical.getCoverImage());
            statement.setString(7, periodical.getDescription());
            statement.setInt(9, periodical.getId());
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void insertPeriodical(Periodical periodical) throws DAOException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_PERIODICAL)) {
            statement.setString(1, periodical.getTitle());
            statement.setBigDecimal(2, periodical.getPrice());
            statement.setInt(3, periodical.getPeriodicity());
            setBookSeriesData(statement, periodical);
            statement.setInt(5, periodical.getTypeId());
            statement.setString(6, periodical.getCoverImage());
            statement.setString(7, periodical.getDescription());
            statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Periodical> findByAuthorId(int authorId) throws DAOException {
        List<Periodical> periodicals = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_AUTHOR_ID)) {
            statement.setInt(1, authorId);
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

    private void setBookSeriesData(PreparedStatement statement, Periodical periodical) throws SQLException {
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
