package com.example.ecommerce.service;

import com.example.ecommerce.model.Coupon;
import com.example.ecommerce.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElse(null);
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Long id, Coupon coupon) {
        if (couponRepository.existsById(id)) {
            coupon.setId(id);
            return couponRepository.save(coupon);
        }
        return null;
    }

    public boolean deleteCoupon(Long id) {
        if (couponRepository.existsById(id)) {
            couponRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Coupon getCouponByCode(String code) {
        return couponRepository.findByCode(code).orElse(null);
    }
}
