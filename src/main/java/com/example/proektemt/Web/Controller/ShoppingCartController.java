package com.example.proektemt.Web.Controller;

import com.example.proektemt.Model.ShoppingCart;
import com.example.proektemt.Service.AuthService;
import com.example.proektemt.Service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;


    public ShoppingCartController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @PostMapping("/{productId}/add-product")
    public String addProductToShoppingCart(@PathVariable Long productId) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), productId);
        } catch (RuntimeException ex) {
            return "redirect:/products?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/products";
    }

    @PostMapping("/{productId}/remove-product")
    public String removeProductToShoppingCart(@PathVariable Long productId) {
        ShoppingCart shoppingCart = this.shoppingCartService.removeProductFromShoppingCart(this.authService.getCurrentUserId(), productId);
        return "redirect:/products";
    }
}
