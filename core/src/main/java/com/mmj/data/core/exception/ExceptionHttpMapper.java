package com.mmj.data.core.exception;

import com.mmj.data.core.util.ToJson;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

/**
 *
 */
@Provider
public class ExceptionHttpMapper implements ExceptionMapper<Exception> {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHttpMapper.class);

    @SuppressWarnings("deprecation")
    @Override
    public Response toResponse(Exception exception) {
        Response response = null;
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            // When catching a BusinessException do not log an error.
            LOG.debug(businessException.getMessage());
            if (exception instanceof NotFoundException) {
                response = Response.status(Response.Status.NOT_FOUND).entity(ToJson.toJson(businessException.getMessages())).build();
            } else if (exception instanceof NotAuthorizedTravelException) {
                response = Response.status(Response.Status.UNAUTHORIZED).entity(ToJson.toJson(businessException.getMessages())).build();
            } else {
                response = Response.status(Response.Status.BAD_REQUEST).entity(ToJson.toJson(businessException.getMessages())).build();
            }
        } else if (exception instanceof SystemException) {
            // When catching a SystemException do not log an error. This must be done where the SystemException is created.
            SystemException systemException = (SystemException) exception;
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ToJson.toJson(systemException.getMessages())).build();
        } else if (exception instanceof EJBException) {
            // When catching a SystemException do not log an error. This must be done where the SystemException is created.
            EJBException ejbException = (EJBException) exception;
            Exception e = ejbException.getCausedByException();
            if (e instanceof SystemException) {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ToJson.toJson(((SystemException) e).getMessages())).build();
            } else {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ToJson.toJson(e.getMessage())).build();
            }
        } else if (exception instanceof ValidationException) {
            if (exception instanceof ValidationException) {
                ValidationException validationException = (ValidationException) exception;
                BusinessException businessException = new BusinessException();
                if (validationException instanceof MethodConstraintViolationException) {
                    MethodConstraintViolationException methodConstraintViolationException = (MethodConstraintViolationException) validationException;
                    Set<MethodConstraintViolation<?>> constraintViolations = methodConstraintViolationException.getConstraintViolations();
                    for (MethodConstraintViolation<?> constraintViolation : constraintViolations) {
                        businessException.addMessage(constraintViolation.getMessage());
                    }
                    String json = ToJson.toJson(businessException.getMessages());
                    response = Response.status(Response.Status.BAD_REQUEST).entity(json).build();
                } else {
                    LOG.error("ExceptionHttpMapper not setup to handle exception {}.", validationException.getClass());
                    LOG.error(validationException.getMessage(), validationException);
                    String json = ToJson.toJson(validationException.getMessage());
                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
                }
            } else {
                LOG.error("ExceptionHttpMapper not setup to handle exception {}.", exception.getClass());
                LOG.error(exception.getMessage(), exception);
                String json = ToJson.toJson(exception.getStackTrace());
                response = Response.status(Response.Status.BAD_REQUEST).entity(json).build();
            }
        } else {
            // When catching an Exception log an error.
            LOG.error(exception.getMessage(), exception);
            SystemException systemException = new SystemException();
            Throwable t = exception;
            if (null != t.getCause()) {
                while (t.getCause() != null) {
                    t = t.getCause();
                    systemException.addMessage(t.getMessage());
                }
            } else {
                systemException.addMessage(t.getMessage());
            }
            String json = ToJson.toJson(systemException.getMessages());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json).build();
        }
        return response;
    }
}

