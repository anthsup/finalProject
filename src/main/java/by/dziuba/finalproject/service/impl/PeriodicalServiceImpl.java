package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.PeriodicalDAOImpl;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class PeriodicalServiceImpl {
    private static final PeriodicalDAOImpl periodicalDAO = new PeriodicalDAOImpl();

    public List<Periodical> getAll(int pageNumber, int periodicalsPerPage) throws ServiceException {
        try {
            return periodicalDAO.findAll(pageNumber, periodicalsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public int getPeriodicalsNumber() throws ServiceException {
        try {
            return periodicalDAO.getPeriodicalsNumber();
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

    public void deleteById(int periodicalId) throws ServiceException {
        try {
            periodicalDAO.deleteById(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void updateById(Periodical periodical) throws ServiceException {
        try {
            periodicalDAO.updateById(periodical);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Periodical> getByAuthorId(int authorId) throws ServiceException {
        try {
            return periodicalDAO.findByAuthorId(authorId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void addPeriodical(Periodical periodical) throws ServiceException {
        try {
            periodicalDAO.insertPeriodical(periodical);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Periodical getByTitle(String periodicalTitle) throws ServiceException {
        try {
            return periodicalDAO.findByTitle(periodicalTitle);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
