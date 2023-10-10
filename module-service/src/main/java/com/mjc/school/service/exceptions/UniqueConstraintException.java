package com.mjc.school.service.exceptions;

import java.io.Serial;

public class UniqueConstraintException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UniqueConstraintException(String message) {
        super(message);
    }

}
