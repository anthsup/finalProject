package by.dziuba.subscription.dao;

import by.dziuba.subscription.dao.exception.DAOException;
import by.dziuba.subscription.entity.Periodical;

import java.util.List;

public interface PeriodicalDAO {
    List<Periodical> findAll(int pageNumber, int periodicalsPerPage) throws DAOException;

    int getPeriodicalsNumber() throws DAOException;

    Periodical findByPeriodicalId(int id) throws DAOException;

    void deleteById(int periodicalId) throws DAOException;

    void updateById(Periodical periodical) throws DAOException;

    void insertPeriodical(Periodical periodical) throws DAOException;

    List<Periodical> findByAuthorId(int authorId) throws DAOException;

    List<Periodical> findByAuthorId(int authorId, int pageNumber, int periodicalsPerPage) throws DAOException;

    Periodical findByTitle(String periodicalTitle) throws DAOException;

    List<Periodical> findByPeriodicalType(int periodicalTypeId, int pageNumber, int periodicalsPerPage) throws DAOException;

    List<Periodical> findByPeriodicity(int periodicity, int pageNumber, int periodicalsPerPage) throws DAOException;
}
