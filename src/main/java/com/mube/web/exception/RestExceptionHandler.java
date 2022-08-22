package com.mube.web.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public RestExceptionHandler() {
        super();
    }

    @ExceptionHandler(CarNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage("Usuario no encontrado");
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);        
    }

    @ExceptionHandler({
      CarIdMismatchException.class,
      ConstraintViolationException.class,
      DataIntegrityViolationException.class,
      InvalidFormatException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex
          .getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}