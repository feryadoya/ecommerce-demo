package com.example.ecommerce.controller;

import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Coupon coupon = couponService.getCouponById(id);
        return coupon != null ? ResponseEntity.ok(coupon) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon newCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.status(201).body(newCoupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        return updatedCoupon != null ? ResponseEntity.ok(updatedCoupon) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        boolean isDeleted = couponService.deleteCoupon(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
