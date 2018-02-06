package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.*;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowPeriodicalEditCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();
    private static final PeriodicalTypeServiceImpl periodicalTypeService = new PeriodicalTypeServiceImpl();
    private static final GenreServiceImpl genreService = new GenreServiceImpl();
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();

            Periodical periodical = periodicalService.getById(Integer.parseInt(requestContent.getRequestParameter("id")));
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

            commandResult.setPage(JspResourceManager.EDIT_PERIODICAL_PAGE);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
