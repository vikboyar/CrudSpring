package org.boyar.boyarkin.utils;

import org.boyar.boyarkin.exception.ResourceNotFoundException;
import org.boyar.boyarkin.exception.UserConflictException;

public class ValidationUtils {

    public static void ifNull(Object obj, String message) throws ResourceNotFoundException {
        if (obj == null) {
            throw new ResourceNotFoundException(message);
        }
    }

    public static void areNotEquals(Object obj1, Object obj2, String message) throws UserConflictException {
        if (!obj1.equals(obj2)) {
            throw new UserConflictException(message);
        }
    }

}
