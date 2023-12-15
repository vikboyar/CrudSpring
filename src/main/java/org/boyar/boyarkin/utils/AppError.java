package org.boyar.boyarkin.utils;

public class AppError {
    private int statusCode;
    private String error;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public AppError() {
    }

    public AppError(int statusCode, String error) {
        this.statusCode = statusCode;
        this.error = error;
    }
}
