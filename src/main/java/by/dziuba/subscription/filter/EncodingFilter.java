package by.dziuba.subscription.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Sets UTF-8 encoding on every request and response.
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD}, initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class EncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);

    private static final String ENCODING_PARAMETER = "encoding";
    private String encoding;

    @Override
    public void init(final FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter(ENCODING_PARAMETER);
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain
            filterChain) throws IOException, ServletException {
        try {
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setCharacterEncoding(encoding);
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Using default request encoding (not utf-8)", e);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}