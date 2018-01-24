package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.PeriodicityDAOImpl;
import by.dziuba.subscription.entity.Periodicity;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.Map;

public class PeriodicityServiceImpl {
    private final static PeriodicityDAOImpl periodicityDao = new PeriodicityDAOImpl();

    public Map<Integer, Periodicity> getAll() throws ServiceException {
        try {
            return periodicityDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
