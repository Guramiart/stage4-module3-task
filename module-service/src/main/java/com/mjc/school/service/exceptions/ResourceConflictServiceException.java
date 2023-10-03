package com.mjc.school.service.exceptions;

import java.io.Serial;

public class ResourceConflictServiceException extends ServiceException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceConflictServiceException(String message, String code, String details) {
        super(message, code, details);
    }

}
