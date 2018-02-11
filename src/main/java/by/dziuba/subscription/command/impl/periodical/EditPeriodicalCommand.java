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

public class EditPeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, JspResourceManager.PERIODICALS_PAGE_COMMAND);
            int periodicalId = Integer.parseInt(requestContent.getRequestParameter("id"));
            genreService.deleteByPeriodicalId(periodicalId);
            genreService.addPeriodicalGenres(periodicalId, createGenresList(requestContent));
            periodicalService.updateById(setUpdatedPeriodicalData(requestContent, periodicalId));
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private Periodical setUpdatedPeriodicalData(RequestContent requestContent, int periodicalId) throws CommandException {
        try {
            Periodical periodical = periodicalService.getByPeriodicalId(periodicalId);
            if (requestContent.getRequestParameter("author") != null) {
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
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private List<Genre> createGenresList(RequestContent requestContent) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : requestContent.getRequestParameterValues("genres")) {
            genres.add(new Genre(genreName));
        }
        return genres;
    }
}
