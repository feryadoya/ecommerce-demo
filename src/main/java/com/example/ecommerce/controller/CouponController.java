package com.example.ecommerce.controller;

import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.service.CouponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@Tag(name = "Coupon Controller", description = "APIs for managing coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping
    @Operation(summary = "Get all coupons", description = "Retrieve a list of all coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get coupon by ID", description = "Retrieve a coupon by its ID")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Coupon coupon = couponService.getCouponById(id);
        return coupon != null ? ResponseEntity.ok(coupon) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new coupon", description = "Create a new coupon with the provided details")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon newCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.status(201).body(newCoupon);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a coupon", description = "Update an existing coupon by its ID")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        return updatedCoupon != null ? ResponseEntity.ok(updatedCoupon) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a coupon", description = "Delete a coupon by its ID")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        boolean isDeleted = couponService.deleteCoupon(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
