package by.dziuba.subscription.command.impl.admin;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;

public class ShowAdminPanelCommand implements Command {
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(JspResourceManager.ADMIN_PANEL_PAGE);
            commandResult.putRequestAttribute("genres", genreService.getAll());
            commandResult.putRequestAttribute("authors", authorService.getAll());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
