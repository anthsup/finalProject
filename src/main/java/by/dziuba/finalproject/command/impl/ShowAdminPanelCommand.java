package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalTypeServiceImpl;

public class ShowAdminPanelCommand implements Command {
    private static final GenreServiceImpl genreService = new GenreServiceImpl();
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();
    private static final PeriodicalTypeServiceImpl periodicalTypeService = new PeriodicalTypeServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            commandResult.putRequestAttribute("genres", genreService.getAll());
            commandResult.putRequestAttribute("authors", authorService.getAll());
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypeService.getAll());
            commandResult.setPage(JspResourceManager.ADMIN_PANEL_PAGE);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
