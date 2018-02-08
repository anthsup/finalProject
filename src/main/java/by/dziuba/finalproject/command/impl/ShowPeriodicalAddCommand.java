package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalTypeServiceImpl;

import java.util.List;

public class ShowPeriodicalAddCommand implements Command {
    private static final PeriodicalTypeServiceImpl periodicalTypeService = new PeriodicalTypeServiceImpl();
    private static final GenreServiceImpl genreService = new GenreServiceImpl();
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            List<PeriodicalType> periodicalTypes = periodicalTypeService.getAll();
            List<Genre> genres = genreService.getAll();
            List<Author> authors = authorService.getAll();

            commandResult.putRequestAttribute("genres", genres);
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypes);
            commandResult.putRequestAttribute("authors", authors);
            commandResult.setPage(JspResourceManager.PERIODICAL_ADD_PAGE);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
