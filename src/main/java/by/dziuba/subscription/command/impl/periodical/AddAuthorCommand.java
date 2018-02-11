package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class AddAuthorCommand implements Command {
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            authorService.addAuthor(requestContent.getRequestParameter("author"));
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
