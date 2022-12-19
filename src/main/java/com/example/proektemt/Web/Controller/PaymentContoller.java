package com.example.proektemt.Web.Controller;

import com.example.proektemt.Model.Product;
import com.example.proektemt.Model.ShoppingCart;
import com.example.proektemt.Model.dto.ChargeRequest;
import com.example.proektemt.Service.AuthService;
import com.example.proektemt.Service.ShoppingCartService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
public class PaymentContoller {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public PaymentContoller(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @GetMapping("/charge")
    public String getCheckoutPage(Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "eur");
            model.addAttribute("amount", (int) (shoppingCart.getProducts().stream().mapToDouble(Product::getPrice).sum() * 100));
            model.addAttribute("stripePublicKey", this.publicKey);
            return "checkout";
        } catch (RuntimeException ex) {
            return "redirect:/product?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest, Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/products?message=Successful Payment";
        } catch (RuntimeException | StripeException ex) {
            return "redirect:/payments/charge?error=" + ex.getLocalizedMessage();
        }
    }

}
