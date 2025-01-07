package com.papasmurfie.utility;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

import java.time.format.DateTimeParseException;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof WebApplicationException) {
            return handleWebApplicationException((WebApplicationException) exception);
        }

        if (exception instanceof DateTimeParseException) {
            return handleDateTimeParseException((DateTimeParseException) exception);
        }

        if (exception instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) exception);
        }

        // Default catch-all
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(
                        500,
                        "Internal Server Error",
                        exception.getMessage() + "\n " + exception.getCause().getMessage()
                ))
                .type("application/json")
                .build();
    }




    private Response handleWebApplicationException(WebApplicationException exception) {
        return Response.status(exception.getResponse().getStatus())
                .entity(exception.getResponse().getEntity())
                .type("application/json")
                .build();
    }

    private Response handleDateTimeParseException(DateTimeParseException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(
                        400,
                        "Invalid Date Format",
                        "The date provided is in an invalid format. Expected format: yyyy-MM-dd"
                ))
                .type("application/json")
                .build();
    }

    private Response handleConstraintViolationException(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(
                        400,
                        "Validation Error",
                        "The input provided is invalid. Please check your data."
                ))
                .type("application/json")
                .build();
    }


}
