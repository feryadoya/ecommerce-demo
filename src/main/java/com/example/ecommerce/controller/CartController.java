package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{customer-id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        Cart cart = cartService.findByCustomer(customerId);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        Cart cart = cartService.updateCartProduct(customerId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestParam Long customerId, @RequestParam Long productId) {
        Cart cart = cartService.updateCartProduct(customerId, productId, 0);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear/{customer-id}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long customerId) {
        Cart cart = cartService.clearCart(customerId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/total/{customer-id}")
    public ResponseEntity<Double> calculateTotal(@PathVariable Long customerId) {
        double total = cartService.calculateTotal(customerId);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/apply-discount")
    public ResponseEntity<Cart> applyDiscount(@RequestParam Long customerId, @RequestParam String couponCode) {
        Cart cart = cartService.applyDiscount(customerId, couponCode);
        return ResponseEntity.ok(cart);
    }
}
