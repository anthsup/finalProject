package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.dao.AuthorDAO;
import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.dao.impl.AuthorDAOImpl;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private static final AuthorDAO authorDao = new AuthorDAOImpl();

    @Override
    public List<Author> getAll() throws ServiceException {
        try {
            return authorDao.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author getById(int authorId) throws ServiceException {
        try {
            return authorDao.findById(authorId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addAuthor(String authorName) throws ServiceException {
        try {
            authorDao.insertAuthor(authorName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(int authorId) throws ServiceException {
        try {
            authorDao.deleteById(authorId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
