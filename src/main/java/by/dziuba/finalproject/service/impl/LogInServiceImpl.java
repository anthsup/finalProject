package by.dziuba.finalproject.service.impl;

import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.LogInService;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;

public class LogInServiceImpl implements LogInService {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public User logIn(String login, String password) throws ServiceException {
        User user = userService.getUserByLogin(login);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
