package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.util.DataValidator;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class AddAuthorCommand implements Command {
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            String authorName = requestContent.getRequestParameter(ParameterConstant.AUTHOR);
            if (!DataValidator.validateAuthorName(authorName)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_AUTHOR_NAME, locale));
            } else {
                authorService.addAuthor(authorName);
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
