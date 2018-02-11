package by.dziuba.subscription.dao;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.entity.Genre;

import java.util.List;
import java.util.Map;

public interface GenreDAO {
    Map<Integer, List<Genre>> findAllPeriodicalGenres() throws DAOException;

    List<Genre> findAll() throws DAOException;

    void deleteByPeriodicalId(int periodicalId) throws DAOException;

    void insertPeriodicalGenres(int periodicalId, List<Genre> periodicalGenres) throws DAOException;

    void insertGenre(String genreName) throws DAOException;

    void deleteGenre(String genreName) throws DAOException;

    List<Genre> findByPeriodicalId(int periodicalId) throws DAOException;

    List<Integer> findPeriodicalsByGenreName(String genreName) throws DAOException;
}
