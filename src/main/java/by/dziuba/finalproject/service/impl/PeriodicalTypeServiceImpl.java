package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.PeriodicalTypeDAOImpl;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class PeriodicalTypeServiceImpl {
    private static final PeriodicalTypeDAOImpl periodicalTypeDao = new PeriodicalTypeDAOImpl();

    public List<PeriodicalType> getAll() throws ServiceException {
        try {
            return periodicalTypeDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public PeriodicalType getById(int periodicalTypeId) throws ServiceException {
        try {
            return periodicalTypeDao.findById(periodicalTypeId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
