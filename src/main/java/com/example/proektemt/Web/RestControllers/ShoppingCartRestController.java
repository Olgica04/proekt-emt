package com.example.proektemt.Web.RestControllers;

import com.example.proektemt.Model.ShoppingCart;
import com.example.proektemt.Model.dto.ChargeRequest;
import com.example.proektemt.Service.AuthService;
import com.example.proektemt.Service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {
    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @PostMapping
    public ShoppingCart createShoppingCart()
    {
        return this.shoppingCartService.createShoppingCart(this.authService.getCurrentUserId());
    }
    @PatchMapping("/{productId}/products")
    public ShoppingCart addProductToShoppingCart(@PathVariable Long productId)
    {
        return this.shoppingCartService.addProductToShoppingCart(
                this.authService.getCurrentUserId(),productId
        );
    }
    @DeleteMapping("/{productId}/products")
    public ShoppingCart removeProductFromShoppingCart(@PathVariable Long productId)
    {
        return this.shoppingCartService.removeProductFromShoppingCart(
                this.authService.getCurrentUserId(),
                productId
        );
    }

    @DeleteMapping
    public ShoppingCart cancelActiveShoppingCart(){
        return this.shoppingCartService.cancelActiveShoppingCart(this.authService.getCurrentUserId());
    }

    @PostMapping("/checkout")
    public ShoppingCart checkoutShoppingCart(){
       // return this.shoppingCartService.checkoutShoppingCart(authService.getCurrentUserId());
        return null;
    }
}


