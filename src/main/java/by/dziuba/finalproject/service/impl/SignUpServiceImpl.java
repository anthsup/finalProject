package by.dziuba.finalproject.service.impl;

import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.LogInService;
import by.dziuba.finalproject.service.SignUpService;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.exception.ServiceException;

public class SignUpServiceImpl implements SignUpService {
    private static final UserService userService = new UserServiceImpl();
    private static final LogInService logInService = new LogInServiceImpl();

    @Override
    public boolean signUp(User user) throws ServiceException {
        User userFromDB = userService.getUserByLogin(user.getLogin());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(logInService.encodePassword(user.getPassword()));
        userService.addUser(user);
        return true;
    }
}
