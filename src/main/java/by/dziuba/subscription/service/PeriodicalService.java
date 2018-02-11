package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public interface PeriodicalService {
    List<Periodical> getAll(int pageNumber, int periodicalsPerPage) throws ServiceException;

    int getPeriodicalsNumber() throws ServiceException;

    Periodical getByPeriodicalId(int id) throws ServiceException;

    void deleteById(int periodicalId) throws ServiceException;

    void updateById(Periodical periodical) throws ServiceException;

    List<Periodical> getByAuthorId(int authorId) throws ServiceException;

    List<Periodical> getByAuthorId(int authorId, int pageNumber, int periodicalsPerPage) throws ServiceException;

    void addPeriodical(Periodical periodical) throws ServiceException;

    Periodical getByTitle(String periodicalTitle) throws ServiceException;

    List<Periodical> getByPeriodicalType(int periodicalTypeId, int pageNumber, int periodicalsPerPage) throws ServiceException;

    List<Periodical> getByPeriodicity(int periodicity, int pageNumber, int periodicalsPerPage) throws ServiceException;
}
