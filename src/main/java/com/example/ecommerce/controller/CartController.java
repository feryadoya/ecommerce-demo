package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart Controller", description = "APIs for managing the shopping cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{customer-id}")
    @Operation(summary = "Get cart", description = "Retrieve the cart for a specific customer")
    public ResponseEntity<Cart> getCart(@PathVariable("customer-id") Long customerId) {
        Cart cart = cartService.findByCustomer(customerId);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    @Operation(summary = "Add to cart", description = "Add a product to the cart for a specific customer")
    public ResponseEntity<Cart> addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        Cart cart = cartService.updateCartProduct(customerId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    @Operation(summary = "Remove from cart", description = "Remove a product from the cart for a specific customer")
    public ResponseEntity<Cart> removeFromCart(@RequestParam Long customerId, @RequestParam Long productId) {
        Cart cart = cartService.updateCartProduct(customerId, productId, 0);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear/{customer-id}")
    @Operation(summary = "Clear cart", description = "Clear the cart for a specific customer")
    public ResponseEntity<Cart> clearCart(@PathVariable("customer-id") Long customerId) {
        Cart cart = cartService.clearCart(customerId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/total/{customer-id}")
    @Operation(summary = "Calculate total", description = "Calculate the total cost of the cart for a specific customer")
    public ResponseEntity<Double> calculateTotal(@PathVariable("customer-id") Long customerId) {
        double total = cartService.calculateTotal(customerId);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/apply-discount")
    @Operation(summary = "Apply discount", description = "Apply a discount to the cart for a specific customer using a coupon code")
    public ResponseEntity<Cart> applyDiscount(@RequestParam Long customerId, @RequestParam String couponCode) {
        Cart cart = cartService.applyDiscount(customerId, couponCode);
        return ResponseEntity.ok(cart);
    }
}
