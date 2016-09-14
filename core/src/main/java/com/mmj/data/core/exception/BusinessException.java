package com.mmj.data.core.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * BusinessException is thrown when some error occurs that we can recover from. i.e. is something happens that we can tell the user about.
 *
 * BusinessException is extending Exception. In a JPA environments that means a transaction will NOT be rolled back
 * when the container intercepts a BusinessException, and the BusinessException will not be wrapped in a EJBException
 *
 * To have a business exceptions force rollback extend this exception and annotate the new exception with
 * @ApplicationException(rollback = true)
 *
 * <p/>
 * When creating a BusinessException do not log an error. But you should log a info or debug.
 * When catching a BusinessException do not log. This must be done where the BusinessException is created.
 */
public class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;
    private List<String> messages = new ArrayList();
    private String errorCode;

    public BusinessException(String message) {
        super(message);
        messages.add(message);
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        messages.add(message);
    }

    public BusinessException(String errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String message, Throwable e) {
        this(message, e);
        this.errorCode = errorCode;
    }

    public BusinessException(List messages) {
        super();
        this.messages = messages;
    }

    public BusinessException() {
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
