package com.papasmurfie.utility;

public class ErrorResponse {

    private int statusCode;
    private String error;
    private String details;

    public ErrorResponse(int statusCode, String error, String details) {
        this.statusCode = statusCode;
        this.error = error;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }
}
