package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.PeriodicalTypeService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalTypeServiceImpl;

import java.util.List;

public class ShowPeriodicalEditCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final PeriodicalTypeService periodicalTypeService = new PeriodicalTypeServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(JspResourceManager.EDIT_PERIODICAL_PAGE);

            Periodical periodical = periodicalService.getByPeriodicalId(Integer.parseInt(requestContent.getRequestParameter("id")));
            List<PeriodicalType> periodicalTypes = periodicalTypeService.getAll();
            List<Genre> periodicalGenres = genreService.getByPeriodicalId(periodical.getId());
            List<Genre> genres = genreService.getAll();

            for (PeriodicalType periodicalType : periodicalTypes) {
                if (periodicalType.getId() == periodical.getTypeId()) {
                    List<Author> authors = authorService.getAll();
                    commandResult.putRequestAttribute("authors", authors);
                }
            }
            commandResult.putRequestAttribute("genres", genres);
            commandResult.putRequestAttribute("periodicalGenres", periodicalGenres);
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypes);
            commandResult.putRequestAttribute("periodical", periodical);

            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
