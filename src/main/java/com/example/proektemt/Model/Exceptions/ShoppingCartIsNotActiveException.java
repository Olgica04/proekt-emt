package com.example.proektemt.Model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShoppingCartIsNotActiveException extends RuntimeException{

    public ShoppingCartIsNotActiveException()
    {

        super(String.format("Shopping cart is not active"));
    }
}
