package com.papasmurfie.utility;

/**
 * Represents an error response to be sent to the client.
 * Contains information about the error status code, error type, and details.
 */
public class ErrorResponse {

    private int statusCode;
    private String error;
    private String details;

    /**
     * Constructs an ErrorResponse with the given status code, error type, and details.
     *
     * @param statusCode The HTTP status code to be included in the error response.
     * @param error The error type or name.
     * @param details A detailed message describing the error.
     */
    public ErrorResponse(int statusCode, String error, String details) {
        this.statusCode = statusCode;
        this.error = error;
        this.details = details;
    }

    /**
     * Gets the HTTP status code associated with this error.
     *
     * @return The status code of the error response.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the error type or name associated with this error.
     *
     * @return The type or name of the error.
     */
    public String getError() {
        return error;
    }

    /**
     * Gets the detailed message describing the error.
     *
     * @return The details of the error.
     */
    public String getDetails() {
        return details;
    }
}
