package by.dziuba.subscription.controller;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandProvider;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.dziuba.subscription.command.CommandResult.RoutingType.FORWARD;
import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

@WebServlet(name = "FrontController", urlPatterns = {"/controller"})
public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestContent requestContent = new RequestContent(req);
            Command command = CommandProvider.defineCommand(requestContent);
            CommandResult commandResult = command.execute(requestContent);
            commandResult.updateRequest(req);

            if (commandResult.getRoutingType().equals(REDIRECT)) {
                resp.sendRedirect(req.getContextPath() + commandResult.getPage());
            } else if (commandResult.getRoutingType().equals(FORWARD)) {
                getServletContext().getRequestDispatcher(commandResult.getPage()).forward(req, resp);
            } else {
                resp.sendError(commandResult.getErrorCode(), commandResult.getErrorMessage());
            }
        } catch (CommandException e) {
            LOGGER.warn(e.getMessage());
            throw new ServletException(e);
        } catch (BadRequestException e) {
            LOGGER.warn(e.getMessage());
            resp.sendError(400, e.getMessage()); //TODO maybe 404?
        }
    }
}
