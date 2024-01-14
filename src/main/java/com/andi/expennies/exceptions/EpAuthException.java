package com.andi.expennies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EpAuthException extends RuntimeException {
    public EpAuthException(String message) {
        super(message);
    }
}
