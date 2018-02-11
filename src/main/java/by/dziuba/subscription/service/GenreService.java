package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface GenreService {
    Map<Integer, List<Genre>> getAllPeriodicalGenres() throws ServiceException;

    List<Genre> getAll() throws ServiceException;

    void deleteByPeriodicalId(int periodicalId) throws ServiceException;

    void addPeriodicalGenres(int periodicalId, List<Genre> periodicalGenres) throws ServiceException;

    List<Genre> getByPeriodicalId(int periodicalId) throws ServiceException;

    void addGenre(String genreName) throws ServiceException;

    void deleteGenre(String genreName) throws ServiceException;

    List<Integer> getPeriodicalsByGenreName(String genreName) throws ServiceException;
}
