package com.example.proektemt.Model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIsNotFoundException extends RuntimeException{
    public UserIsNotFoundException(String id)
    {
        super(String.format("User with %d is not found", id));
    }
}
