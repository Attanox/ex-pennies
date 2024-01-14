package com.andi.expennies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EpBadRequestException extends RuntimeException {
    public EpBadRequestException(String message) {
        super(message);
    }
}
