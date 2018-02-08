package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;

public class AddAuthorCommand implements Command {
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            authorService.addAuthor(requestContent.getRequestParameter("author"));
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
