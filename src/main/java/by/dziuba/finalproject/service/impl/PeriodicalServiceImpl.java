package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.PeriodicalDAOImpl;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class PeriodicalServiceImpl {
    private static final PeriodicalDAOImpl periodicalDAO = new PeriodicalDAOImpl();

    public List<Periodical> getAll() throws ServiceException {
        try {
            return periodicalDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Periodical getById(int id) throws ServiceException {
        try {
            return periodicalDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Map<Integer, PeriodicalType> getAllTypes() throws ServiceException {
        try {
            return periodicalDAO.findAllTypes();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteById(int periodicalId) throws ServiceException {
        try {
            periodicalDAO.deleteById(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
