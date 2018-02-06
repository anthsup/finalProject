package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.GenreDAOImpl;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class GenreServiceImpl {
    private static final GenreDAOImpl genreDao = new GenreDAOImpl();

    public Map<Integer, List<Genre>> getAllPeriodicalGenres() throws ServiceException {
        try {
            return genreDao.findAllPeriodicalGenres();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Genre> getAll() throws ServiceException {
        try {
            return genreDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteByPeriodicalId(int periodicalId) throws ServiceException {
        try {
            genreDao.deleteByPeriodicalId(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void insertPeriodicalGenres(int periodicalId, List<Genre> periodicalGenres) throws ServiceException {
        try {
            genreDao.insertPeriodicalGenres(periodicalId, periodicalGenres);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Genre> getByPeriodicalId(int periodicalId) throws ServiceException {
        try {
            return genreDao.findByPeriodicalId(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
