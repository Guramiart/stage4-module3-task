package com.mjc.school.service.exceptions;

import java.io.Serial;

public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String code;
    private final String details;

    public ServiceException(String message, String code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }

}
