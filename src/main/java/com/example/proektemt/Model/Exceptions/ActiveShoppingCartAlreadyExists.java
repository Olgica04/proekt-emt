package com.example.proektemt.Model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ActiveShoppingCartAlreadyExists extends RuntimeException{
    public ActiveShoppingCartAlreadyExists()
    {
        super(String.format("Active shopping cart already exists!"));
    }
}
//42:06 sum stignata
//napraveno category, product, manufacturer i user celo sega treba da napravish za shopping cart