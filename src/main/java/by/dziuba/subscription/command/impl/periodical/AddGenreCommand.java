package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class AddGenreCommand implements Command {
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            genreService.addGenre(requestContent.getRequestParameter("genre"));
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
