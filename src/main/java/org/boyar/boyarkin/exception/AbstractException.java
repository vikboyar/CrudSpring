package org.boyar.boyarkin.exception;


import java.io.Serializable;

public abstract class AbstractException extends RuntimeException implements Serializable {
    protected String error;

    public AbstractException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
