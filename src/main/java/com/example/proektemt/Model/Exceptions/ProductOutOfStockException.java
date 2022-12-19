package com.example.proektemt.Model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException()
    {
        super(String.format("The product is out of stock!"));
    }
}
