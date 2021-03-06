package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalTypeService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalTypeServiceImpl;

import java.util.List;

public class ShowPeriodicalAddCommand implements Command {
    private static final PeriodicalTypeService periodicalTypeService = new PeriodicalTypeServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(JspPath.PERIODICAL_ADD_PAGE);
            List<PeriodicalType> periodicalTypes = periodicalTypeService.getAll();
            List<Genre> genres = genreService.getAll();
            List<Author> authors = authorService.getAll();

            commandResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICAL_TYPES, periodicalTypes);
            commandResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
