package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class AddPeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, JspResourceManager.ADMIN_PANEL_PAGE_COMMAND);
            Periodical periodical = setPeriodicalData(requestContent);
            periodicalService.addPeriodical(periodical);
            genreService.addPeriodicalGenres(periodicalService.getByTitle(periodical.getTitle()).getId(), createGenresList(requestContent));
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private Periodical setPeriodicalData(RequestContent requestContent) {
        Periodical periodical = new Periodical();
        if (!requestContent.getRequestParameter("author").isEmpty()) {
            periodical.setAuthorId(Integer.parseInt(requestContent.getRequestParameter("author")));
            periodical.setBooksAmount(Integer.parseInt(requestContent.getRequestParameter("books")));
        }
        periodical.setCoverImage(requestContent.getRequestParameter("coverImage"));
        periodical.setDescription(requestContent.getRequestParameter("description"));
        periodical.setPeriodicity(Integer.parseInt(requestContent.getRequestParameter("periodicity")));
        periodical.setPrice(new BigDecimal(requestContent.getRequestParameter("price")));
        periodical.setTitle(requestContent.getRequestParameter("title"));
        periodical.setTypeId(Integer.parseInt(requestContent.getRequestParameter("type")));
        return periodical;
    }

    private List<Genre> createGenresList(RequestContent requestContent) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : requestContent.getRequestParameterValues("genres")) {
            genres.add(new Genre(genreName));
        }
        return genres;
    }
}
