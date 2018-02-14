package by.dziuba.subscription.controller;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandProvider;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.exception.CommandException;
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

/**
 * Front Controller manages requests and responses. It executes commands which were called,
 * passing them wrapped request data. It then gets results and forwards/redirects to corresponding pages.
 * @see RequestContent
 * @see CommandResult
 */
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

            if (REDIRECT.equals(commandResult.getRoutingType())) {
                resp.sendRedirect(req.getContextPath() + commandResult.getPage());
            } else if (FORWARD.equals(commandResult.getRoutingType())) {
                getServletContext().getRequestDispatcher(commandResult.getPage()).forward(req, resp);
            } else {
                resp.sendError(commandResult.getErrorCode(), commandResult.getErrorMessage());
            }
        } catch (CommandException e) {
            LOGGER.warn(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            LOGGER.warn(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
