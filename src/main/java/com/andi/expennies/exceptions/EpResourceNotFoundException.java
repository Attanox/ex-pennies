package com.andi.expennies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EpResourceNotFoundException extends RuntimeException {
    public EpResourceNotFoundException(String message) {
        super(message);
    }
}
