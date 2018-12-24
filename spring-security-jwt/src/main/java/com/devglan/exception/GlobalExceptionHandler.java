package com.devglan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

        @ExceptionHandler(Exception.class)
        public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                    request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(DuplicateEntityException.class)
        public final ResponseEntity<ErrorDetails> handleDuplicateEntityException(DuplicateEntityException ex, WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                    request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.FOUND);
        }

        @ExceptionHandler(TokenExpirationException.class)
        public final ResponseEntity<ErrorDetails> handleTokenExpirationException(TokenExpirationException ex, WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                    request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.FOUND);
        }

    }
