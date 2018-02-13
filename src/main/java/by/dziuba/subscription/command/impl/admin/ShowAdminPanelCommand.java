package by.dziuba.subscription.command.impl.admin;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;

public class ShowAdminPanelCommand implements Command {
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(JspPath.ADMIN_PANEL_PAGE);
            commandResult.putRequestAttribute(ParameterConstant.GENRES, genreService.getAll());
            commandResult.putRequestAttribute(ParameterConstant.AUTHORS, authorService.getAll());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
