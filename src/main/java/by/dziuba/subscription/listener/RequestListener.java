package by.dziuba.subscription.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.io.UnsupportedEncodingException;

@WebListener
public class RequestListener implements ServletRequestListener {
    private static final Logger LOGGER = LogManager.getLogger(ServletRequestListener.class);
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest request = servletRequestEvent.getServletRequest();
        try {
            if (request.getCharacterEncoding() == null) {
                request.setCharacterEncoding("UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn(e.getMessage());
            LOGGER.info("Using default request encoding (not utf-8)");
        }
    }
}
