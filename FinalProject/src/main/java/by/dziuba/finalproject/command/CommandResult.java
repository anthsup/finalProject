package by.dziuba.finalproject.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandResult {
    private String page;
    private boolean redirected;
    private String errorMessage;
    private Integer errorCode;
    private boolean sessionInvalidated;
    private Map<String, Object> sessionAttributes = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isRedirected() {
        return redirected;
    }

    public void setRedirected(boolean redirected) {
        this.redirected = redirected;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setSessionInvalidated(boolean sessionInvalidated) {
        this.sessionInvalidated = sessionInvalidated;
    }

    public void updateRequest(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach(request.getSession()::setAttribute);
        if (sessionInvalidated) {
            request.getSession().invalidate();
        }
    }

    public Object putRequestAttribute(String attribute, Object value) {
        return requestAttributes.put(attribute, value);
    }

    public Object putSessionAttribute(String attribute, Object value) {
        return sessionAttributes.put(attribute, value);
    }
}
