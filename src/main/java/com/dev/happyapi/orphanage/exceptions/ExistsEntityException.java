package com.dev.happyapi.orphanage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistsEntityException extends RuntimeException {
    public ExistsEntityException(String msg) {
        super(msg);
    }
}
