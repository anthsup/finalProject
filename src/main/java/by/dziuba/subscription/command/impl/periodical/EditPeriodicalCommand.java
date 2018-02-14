package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.util.DataValidator;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
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
            CommandResult commandResult = new CommandResult(REDIRECT, JspPath.PERIODICALS_PAGE_COMMAND);
            int periodicalId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.PERIODICAL_ID));

            Periodical periodical = periodicalService.getByPeriodicalId(periodicalId);
            if (!DataValidator.validatePeriodical(periodical)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_PERIODICAL, locale));
            }

            genreService.deleteByPeriodicalId(periodicalId);
            genreService.addPeriodicalGenres(periodicalId, createGenresList(requestContent));
            periodicalService.updateById(setUpdatedPeriodicalData(requestContent, periodical));
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private Periodical setUpdatedPeriodicalData(RequestContent requestContent, Periodical periodical) {
        if (requestContent.getRequestParameter(ParameterConstant.AUTHOR) != null) {
            periodical.setAuthorId(Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.AUTHOR)));
            periodical.setBooksAmount(Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.BOOKS_AMOUNT)));
        }
        periodical.setCoverImage(requestContent.getRequestParameter(ParameterConstant.COVER_IMAGE));
        periodical.setDescription(requestContent.getRequestParameter(ParameterConstant.DESCRIPTION));
        periodical.setPeriodicity(Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.PERIODICITY)));
        periodical.setPrice(new BigDecimal(requestContent.getRequestParameter(ParameterConstant.PRICE)));
        periodical.setTitle(requestContent.getRequestParameter(ParameterConstant.TITLE));
        periodical.setTypeId(Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.TYPE)));
        return periodical;
    }

    private List<Genre> createGenresList(RequestContent requestContent) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : requestContent.getRequestParameterValues(ParameterConstant.GENRES)) {
            genres.add(new Genre(genreName));
        }
        return genres;
    }
}
