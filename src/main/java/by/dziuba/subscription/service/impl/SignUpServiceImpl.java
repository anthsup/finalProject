package by.dziuba.subscription.service.impl;

import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.LogInService;
import by.dziuba.subscription.service.SignUpService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;

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
