package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    List<Author> getAll() throws ServiceException;

    Author getById(int authorId) throws ServiceException;

    void addAuthor(String authorName) throws ServiceException;

    void deleteById(int authorId) throws ServiceException;
}
