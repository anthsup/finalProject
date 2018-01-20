package by.dziuba.finalproject.service.impl;


import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.PasswordReminderService;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.service.EmailSenderService;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.util.PasswordGenerator;

public class PasswordReminderServiceImpl implements PasswordReminderService {
    private static final UserService userService = new UserServiceImplementation();
    private static final EmailSenderService emailSenderService = new EmailSenderServiceImplementation();

    @Override
    public void remindPassword(String email) throws ServiceException {
        User user = userService.getUserByEmail(email);
        if (user == null || email == null) {
            return;
        }
        String newPassword = PasswordGenerator.generate();
        userService.updateUserPassword(user.getId(), newPassword);
        user.setPassword(newPassword);
        String message = generateRemindPasswordMessage(user, newPassword);
        emailSenderService.sendEmail(message,"New password", email);
    }
    private String generateRemindPasswordMessage(User user, String newPassword) {
        StringBuilder message = new StringBuilder();
        message.append("Hello dear " + user.getUserRealName() + " \r\n");
        message.append("your new authentication information is : \r\n");
        message.append("login: \""+user.getLogin()+"\" \r\n");
        message.append("password: \""+newPassword+"\" \r\n");
        message.append("With regards Movie Rating app.");
        return message.toString();
    }
}
