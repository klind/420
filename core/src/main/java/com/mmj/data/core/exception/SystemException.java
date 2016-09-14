package com.mmj.data.core.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * SystemException is thrown when some error occurs that we can't recover from. i.e. some IO exception
 *
 * SystemException is extending RuntimeException. In a JPA environments that means a transaction will be rolled back
 * when the container intercepts a SystemException, and the SystemException will be wrapped in an EJBException
 *
 * <p/>
 * When throwing a new SystemException log an error.
 * When catching a SystemException do not log an error. This must be done where the SystemException is created.
 */
public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private List<String> messages = new ArrayList();
    private String errorCode;

    public SystemException(String message) {
        super(message);
        messages.add(message);
    }

    public SystemException(String message, Throwable e) {
        super(message, e);
        messages.add(message);
    }

    public SystemException(String errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public SystemException(String errorCode, String message, Throwable e) {
        this(message, e);
        this.errorCode = errorCode;
    }

    public SystemException(List messages) {
        super();
        this.messages = messages;
    }

    public SystemException() {
        super();
    }

    public void addMessage(String msg) {
        messages.add(msg);
    }

    public List getMessages() {
        return messages;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        if (!messages.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String message : messages) {
                builder.append(message).append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } else {
            return "The exception does no contains any messages";
        }

    }
}
