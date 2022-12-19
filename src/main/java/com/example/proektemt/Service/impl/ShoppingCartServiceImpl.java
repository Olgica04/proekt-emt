package com.example.proektemt.Service.impl;

import com.example.proektemt.Model.Enumerations.CartStatus;
import com.example.proektemt.Model.Exceptions.*;
import com.example.proektemt.Model.Product;
import com.example.proektemt.Model.ShoppingCart;
import com.example.proektemt.Model.User;
import com.example.proektemt.Model.dto.ChargeRequest;
import com.example.proektemt.Repository.ShoppingCartRepository;
import com.example.proektemt.Service.PaymentService;
import com.example.proektemt.Service.ProductService;
import com.example.proektemt.Service.ShoppingCartService;
import com.example.proektemt.Service.UserService;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final ProductService productService;
    private final PaymentService paymentService;

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserService userService, ProductService productService, PaymentService paymentService) {
        this.userService = userService;
        this.productService = productService;
        this.paymentService = paymentService;
        this.shoppingCartRepository=shoppingCartRepository;
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
    }

    @Override
    public ShoppingCart createShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED))
        {
            throw new ActiveShoppingCartAlreadyExists();
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addProductToShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
        Product product = this.productService.findById(productId);
        List<Product> products = shoppingCart.getProducts();
        for(Product p : products)
        {
            if(p.getId().equals(productId))
            {
                throw new ProductIsAlreadyInShoppingCartException(product.getName());
            }
        }
        products.add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCartOrCreateNew(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if(shoppingCart==null)
        {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(this.userService.findById(userId));
            shoppingCart = this.shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    @Transactional
    public ShoppingCart removeProductFromShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
        shoppingCart.setProducts(
                shoppingCart.getProducts()
                .stream()
                .filter(item -> !item.getId().equals(productId))
                .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId,CartStatus.CREATED);
        if(shoppingCart == null)
        {
            throw new ShoppingCartIsNotActiveException();
        }
        shoppingCart.setStatus(CartStatus.CANCELLED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) throws StripeException {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId,CartStatus.CREATED);
        if(shoppingCart == null)
        {
            throw new ShoppingCartIsNotActiveException();
        }

        List<Product> products = shoppingCart.getProducts();
        float price = 0;

        for(Product product : products)
        {
            if(product.getQuantity() <=0 )
            {
                throw new ProductOutOfStockException();
            }
            product.setQuantity(product.getQuantity()-1);
            price+=product.getPrice();
        }
        Charge charge=null;
        try {
            charge = this.paymentService.pay(chargeRequest);
        } catch (CardException | AuthenticationException  | InvalidRequestException e) {
            throw new TransactionFailedException(userId, e.getMessage());
        }

        //paymentService.pay(price);
        shoppingCart.setProducts(products);
        shoppingCart.setStatus(CartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
