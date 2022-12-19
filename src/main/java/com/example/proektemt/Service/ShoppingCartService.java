package com.example.proektemt.Service;

import com.example.proektemt.Model.ShoppingCart;
import com.example.proektemt.Model.dto.ChargeRequest;
import com.stripe.exception.StripeException;
import org.springframework.transaction.annotation.Transactional;

public interface ShoppingCartService {

    ShoppingCart findActiveShoppingCartByUsername(String userId);

    ShoppingCart createShoppingCart(String userId);

    ShoppingCart addProductToShoppingCart(String userId, Long productId);

    ShoppingCart getActiveShoppingCartOrCreateNew(String userId);

    ShoppingCart removeProductFromShoppingCart(String userId, Long productId);

    ShoppingCart cancelActiveShoppingCart(String userId);

    @Transactional
    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) throws StripeException;


}
