package com.example.proektemt.Model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ThereAreProductsWithThisManufacturer extends RuntimeException{
    public ThereAreProductsWithThisManufacturer()
    {
        super(String.format("There are products with this manufacturer"));
    }
}
