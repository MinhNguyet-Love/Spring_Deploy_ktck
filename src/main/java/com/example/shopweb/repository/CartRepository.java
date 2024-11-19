package com.example.shopweb.repository;

import com.example.shopweb.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Additional custom queries (if needed) can go here
}
