package com.mjc.school.web.exceptions.handler;

import com.mjc.school.web.exceptions.ErrorResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.exceptions.ServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object>  handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                   @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status,
                                                                   @NonNull WebRequest request) {
        String details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorResponse errorResponse = new ErrorResponse(
                ServiceErrorCode.VALIDATION_ERROR.getErrorCode(),
                ServiceErrorCode.VALIDATION_ERROR.getErrorMessage(),
                details
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      @NonNull HttpHeaders headers,
                                                                      @NonNull HttpStatus status,
                                                                      @NonNull WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                ServiceErrorCode.API_NOT_SUPPORTED.getErrorCode(),
                ServiceErrorCode.API_NOT_SUPPORTED.getErrorMessage(),
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({ NotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFoundConflict(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                ServiceErrorCode.RESOURCE_NOT_FOUND.getErrorCode(),
                ServiceErrorCode.RESOURCE_NOT_FOUND.getErrorMessage(),
                ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ServiceException.class })
    protected ResponseEntity<Object> handleUnexpectedConflict(ServiceException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                ServiceErrorCode.NOT_SUPPORTED.getErrorCode(),
                ServiceErrorCode.NOT_SUPPORTED.getErrorMessage(),
                ex.getMessage()
        ), HttpStatus.NOT_IMPLEMENTED);
    }
}
