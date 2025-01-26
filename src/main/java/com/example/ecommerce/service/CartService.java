package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Status;
import com.example.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CouponService couponService;

    public Cart findByCustomer(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        Optional<Cart> cart = cartRepository.findByCustomerIdAndStatus(customerId, Status.CURRENT);
        if (!cart.isPresent()) {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setStatus(Status.CURRENT);
            return cartRepository.save(newCart);
        }
        return cart.get();
    }

    public Cart updateCartProduct(Long customerId, Long productId, int quantity) {
        Cart cart = findByCustomer(customerId);
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (quantity > 0) {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long customerId) {
        Cart cart = findByCustomer(customerId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

    public double calculateTotal(Long customerId) {
        Cart cart = findByCustomer(customerId);
        return cart.calculateTotal();
    }

    public Cart applyDiscount(Long customerId, String couponCode) {
        Cart cart = findByCustomer(customerId);
        Coupon coupon = couponService.getCouponByCode(couponCode);
        if (coupon == null) {
            throw new IllegalArgumentException("Invalid coupon code");
        }
        cart.setCoupon(coupon);
        return cartRepository.save(cart);
    }
}