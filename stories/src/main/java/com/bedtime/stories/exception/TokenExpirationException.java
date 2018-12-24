package com.bedtime.stories.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class TokenExpirationException extends RuntimeException {

    public TokenExpirationException(String exception) {
        super(exception);
    }
}