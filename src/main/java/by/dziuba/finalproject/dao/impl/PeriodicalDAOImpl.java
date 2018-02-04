package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.PeriodicalType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicalDAOImpl {
    private static final String SELECT_ALL = "SELECT * FROM periodical";
    private static final String SELECT_BY_ID = "SELECT * FROM periodical WHERE id = ?";
    private static final String SELECT_ALL_TYPES = "SELECT * FROM periodical_type";
    private static final String DELETE_BY_ID = "DELETE FROM periodical WHERE id = ?";


    public List<Periodical> findAll() throws DAOException {
        List<Periodical> periodicals = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
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

    public Map<Integer, PeriodicalType> findAllTypes() throws DAOException {
        Map<Integer, PeriodicalType> periodicalTypes = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL_TYPES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicalTypes.put(resultSet.getInt("id"), createPeriodicalType(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicalTypes;
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

    private Periodical createPeriodical(ResultSet resultSet) throws SQLException {
        Periodical periodical = new Periodical();
        periodical.setId(resultSet.getInt("id"));
        periodical.setTitle(resultSet.getString("title"));
        periodical.setPrice(BigDecimal.valueOf(resultSet.getDouble("price")));
        periodical.setPeriodicityId(resultSet.getInt("periodicity_id"));
        periodical.setAuthorId(resultSet.getInt("author_id"));
        periodical.setTypeId(resultSet.getInt("periodical_type_id"));
        periodical.setCoverImage(resultSet.getString("coverImage"));
        periodical.setDescription(resultSet.getString("description"));
        periodical.setBooksAmount(resultSet.getInt("booksAmount"));
        return periodical;
    }

    private PeriodicalType createPeriodicalType(ResultSet selected) throws SQLException {
        PeriodicalType periodicalType = new PeriodicalType();
        periodicalType.setId(selected.getInt("id"));
        periodicalType.setName(selected.getString("type"));
        return periodicalType;
    }
}
