package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.PeriodicalTypeDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.PeriodicalTypeDAOImpl;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.PeriodicalTypeService;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class PeriodicalTypeServiceImpl implements PeriodicalTypeService {
    private static final PeriodicalTypeDAO periodicalTypeDao = new PeriodicalTypeDAOImpl();

    @Override
    public List<PeriodicalType> getAll() throws ServiceException {
        try {
            return periodicalTypeDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PeriodicalType getById(int periodicalTypeId) throws ServiceException {
        try {
            return periodicalTypeDao.findById(periodicalTypeId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
