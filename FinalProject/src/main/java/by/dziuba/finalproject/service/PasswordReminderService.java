package by.dziuba.finalproject.service;

import by.dziuba.finalproject.service.exception.ServiceException;

public interface PasswordReminderService {
    void remindPassword(String email) throws ServiceException;
}
