package by.dziuba.subscription.dao.impl;

import by.dziuba.subscription.dao.PeriodicalTypeDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.database.DBConnectionPool;
import by.dziuba.subscription.database.exception.DBException;
import by.dziuba.subscription.entity.PeriodicalType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalTypeDAOImpl implements PeriodicalTypeDAO {
    private static final String SELECT_ALL = "SELECT * FROM periodical_type";
    private static final String SELECT_BY_ID = "SELECT * FROM periodical_type WHERE id = ?";

    @Override
    public List<PeriodicalType> findAll() throws DAOException {
        List<PeriodicalType> periodicalTypes = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicalTypes.add(createPeriodicalType(resultSet));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicalTypes;
    }

    @Override
    public PeriodicalType findById(int periodicalTypeId) throws DAOException {
        PeriodicalType periodicalType = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, periodicalTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicalType = createPeriodicalType(resultSet);
                }
            }
        } catch (DBException | SQLException e) {
            throw new DAOException(e);
        }
        return periodicalType;
    }

    private PeriodicalType createPeriodicalType(ResultSet selected) throws SQLException {
        PeriodicalType periodicalType = new PeriodicalType();
        periodicalType.setId(selected.getInt("id"));
        periodicalType.setName(selected.getString("type"));
        return periodicalType;
    }
}
