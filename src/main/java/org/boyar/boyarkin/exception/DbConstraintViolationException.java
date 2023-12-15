package org.boyar.boyarkin.exception;

public class DbConstraintViolationException extends AbstractException{
    public DbConstraintViolationException(String error) {
        super(error);
    }
}
