package com.mjc.school.web.exceptions;

public record ErrorResponse(String code, String message, String details) {
}
