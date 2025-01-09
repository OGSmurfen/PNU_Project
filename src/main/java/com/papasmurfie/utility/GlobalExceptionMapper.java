package com.papasmurfie.utility;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

import java.time.format.DateTimeParseException;

/**
 * Global exception handler for handling different types of exceptions thrown in the application.
 * This class is annotated with {@link Provider} and implements the {@link ExceptionMapper} interface to map exceptions to HTTP responses.
 * It provides custom handling for {@link WebApplicationException}, {@link DateTimeParseException}, and {@link ConstraintViolationException}.
 * If an unhandled exception occurs, a generic 500 Internal Server Error response is returned.
 * <p>
 * The {@link Provider} annotation marks this class as a global exception handler for the application,
 * allowing it to automatically intercept and process exceptions across all JAX-RS resources.
 * </p>
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    /**
     * Maps the provided exception to an appropriate HTTP response.
     *
     * @param exception The exception that occurred during the request processing.
     * @return A Response object containing the error details and corresponding HTTP status code.
     */
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
                        exception.getMessage()
                ))
                .type("application/json")
                .build();
    }

    /**
     * Handles {@link WebApplicationException} by returning the response contained in the exception.
     *
     * @param exception The WebApplicationException that was thrown.
     * @return A Response object with the status and entity of the exception's response.
     */
    private Response handleWebApplicationException(WebApplicationException exception) {
        return Response.status(exception.getResponse().getStatus())
                .entity(exception.getResponse().getEntity())
                .type("application/json")
                .build();
    }

    /**
     * Handles {@link DateTimeParseException} by returning a 400 Bad Request response with a detailed error message.
     *
     * @param exception The DateTimeParseException that was thrown.
     * @return A Response object with a 400 status and an error message indicating the invalid date format.
     */
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

    /**
     * Handles {@link ConstraintViolationException} by returning a 400 Bad Request response with a detailed error message.
     *
     * @param exception The ConstraintViolationException that was thrown.
     * @return A Response object with a 400 status and an error message indicating invalid input data.
     */
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
