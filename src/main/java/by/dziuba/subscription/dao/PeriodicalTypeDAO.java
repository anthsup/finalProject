package by.dziuba.subscription.dao;

import by.dziuba.subscription.exception.DAOException;
import by.dziuba.subscription.entity.PeriodicalType;

import java.util.List;

public interface PeriodicalTypeDAO {
    List<PeriodicalType> findAll() throws DAOException;

    PeriodicalType findById(int periodicalTypeId) throws DAOException;
}
