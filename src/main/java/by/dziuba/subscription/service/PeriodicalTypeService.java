package by.dziuba.subscription.service;

import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.exception.ServiceException;

import java.util.List;

public interface PeriodicalTypeService {
    List<PeriodicalType> getAll() throws ServiceException;

    PeriodicalType getById(int periodicalTypeId) throws ServiceException;
}
