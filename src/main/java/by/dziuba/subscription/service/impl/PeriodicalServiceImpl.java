package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.PeriodicalDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.PeriodicalDAOImpl;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class PeriodicalServiceImpl implements PeriodicalService {
    private static final PeriodicalDAO periodicalDAO = new PeriodicalDAOImpl();

    @Override
    public List<Periodical> getAll(int pageNumber, int periodicalsPerPage) throws ServiceException {
        try {
            return periodicalDAO.findAll(pageNumber, periodicalsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getPeriodicalsNumber() throws ServiceException {
        try {
            return periodicalDAO.getPeriodicalsNumber();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Periodical getByPeriodicalId(int id) throws ServiceException {
        try {
            return periodicalDAO.findByPeriodicalId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(int periodicalId) throws ServiceException {
        try {
            periodicalDAO.deleteById(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateById(Periodical periodical) throws ServiceException {
        try {
            periodicalDAO.updateById(periodical);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Periodical> getByAuthorId(int authorId) throws ServiceException {
        try {
            return periodicalDAO.findByAuthorId(authorId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Periodical> getByAuthorId(int authorId, int pageNumber, int periodicalsPerPage) throws ServiceException {
        try {
            return periodicalDAO.findByAuthorId(authorId, pageNumber, periodicalsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addPeriodical(Periodical periodical) throws ServiceException {
        try {
            periodicalDAO.insertPeriodical(periodical);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Periodical getByTitle(String periodicalTitle) throws ServiceException {
        try {
            return periodicalDAO.findByTitle(periodicalTitle);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Periodical> getByPeriodicalType(int periodicalTypeId, int pageNumber, int periodicalsPerPage) throws ServiceException {
        try {
            return periodicalDAO.findByPeriodicalType(periodicalTypeId, pageNumber, periodicalsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Periodical> getByPeriodicity(int periodicity, int pageNumber, int periodicalsPerPage) throws ServiceException {
        try {
            return periodicalDAO.findByPeriodicity(periodicity, pageNumber, periodicalsPerPage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
