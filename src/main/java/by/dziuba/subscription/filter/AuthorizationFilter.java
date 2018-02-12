package by.dziuba.subscription.filter;

import by.dziuba.subscription.command.CommandProvider;
import by.dziuba.subscription.command.CommandType;
import by.dziuba.subscription.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class AuthorizationFilter implements Filter {
    private Set<String> adminCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        EnumSet<CommandType> adminCommandTypes = EnumSet.range(CommandType.BAN_USER, CommandType.ADD_PERIODICAL);
        adminCommandTypes.forEach(commandType -> adminCommands.add(commandType.name()));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null || currentSession.getAttribute("user") == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            authorize(servletRequest, servletResponse, filterChain);
        }
    }

    private void authorize(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User currentUser = (User) request.getSession().getAttribute("user");
        boolean isAdmin = currentUser.isAdmin();
        String command = CommandProvider.convertCommandType(request.getParameter("command"));
        if (isAdmin || !adminCommands.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
    }
}
