package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.GenreDAOImpl;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class GenreServiceImpl {
    private static final GenreDAOImpl genreDao = new GenreDAOImpl();

    public Map<Integer, List<Genre>> getAll() throws ServiceException {
        try {
            return genreDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
