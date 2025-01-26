package com.example.ecommerce.repository;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    @Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId AND c.status = :status")
    Optional<Cart> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") Status status);
}
