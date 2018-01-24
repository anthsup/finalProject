package by.dziuba.subscription.controller;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.util.CommandProvider;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
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

@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

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
            CommandProvider client = new CommandProvider();
            RequestContent requestContent = new RequestContent(req);
            Command command = client.defineCommand(requestContent);
            CommandResult commandResult = command.execute(requestContent);
            commandResult.updateRequest(req);

            if (commandResult.isRedirected() && commandResult.getErrorCode() == null) {
                resp.sendRedirect(req.getContextPath() + commandResult.getPage());
            } else if (commandResult.getErrorCode() == null) {
                getServletContext().getRequestDispatcher(commandResult.getPage()).forward(req, resp);
            } else {
                resp.sendError(commandResult.getErrorCode(), commandResult.getErrorMessage());
            }
        } catch (CommandException e){
            LOGGER.warn(e.getMessage());
            throw new ServletException(e);
        } catch (BadRequestException e) {
            LOGGER.warn(e.getMessage());
            resp.sendError(400, e.getMessage());
        }
    }
}
