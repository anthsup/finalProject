package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.GenreDAO;
import by.dziuba.subscription.exception.DAOException;
import by.dziuba.subscription.dao.impl.GenreDAOImpl;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class GenreServiceImpl implements GenreService {
    private static final GenreDAO genreDao = new GenreDAOImpl();

    @Override
    public Map<Integer, List<Genre>> getAllPeriodicalGenres() throws ServiceException {
        try {
            return genreDao.findAllPeriodicalGenres();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Genre> getAll() throws ServiceException {
        try {
            return genreDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByPeriodicalId(int periodicalId) throws ServiceException {
        try {
            genreDao.deleteByPeriodicalId(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addPeriodicalGenres(int periodicalId, List<Genre> periodicalGenres) throws ServiceException {
        try {
            genreDao.insertPeriodicalGenres(periodicalId, periodicalGenres);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Genre> getByPeriodicalId(int periodicalId) throws ServiceException {
        try {
            return genreDao.findByPeriodicalId(periodicalId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addGenre(String genreName) throws ServiceException {
        try {
            genreDao.insertGenre(genreName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteGenre(String genreName) throws ServiceException {
        try {
            genreDao.deleteGenre(genreName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Integer> getPeriodicalsByGenreName(String genreName) throws ServiceException {
        try {
            return genreDao.findPeriodicalsByGenreName(genreName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
