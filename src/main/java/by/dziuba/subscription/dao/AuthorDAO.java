package by.dziuba.subscription.dao;

import by.dziuba.subscription.exception.DAOException;
import by.dziuba.subscription.entity.Author;

import java.util.List;

public interface AuthorDAO {
    List<Author> findAll() throws DAOException;

    Author findById(int authorId) throws DAOException;

    void insertAuthor(String authorName) throws DAOException;

    void deleteById(int authorId) throws DAOException;
}
