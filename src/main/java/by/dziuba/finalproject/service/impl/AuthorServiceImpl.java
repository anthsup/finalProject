package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.AuthorDAOImpl;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class AuthorServiceImpl {
    private static final AuthorDAOImpl authorDao = new AuthorDAOImpl();

    public List<Author> getAll() throws ServiceException {
        try {
            return authorDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Author getById(int authorId) throws ServiceException {
        try {
            return authorDao.findById(authorId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
