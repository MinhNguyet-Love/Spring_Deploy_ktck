// src/main/java/com/example/shopweb/repository/CartItemRepository.java
package com.example.shopweb.repository;

import com.example.shopweb.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Có thể thêm các truy vấn tùy chỉnh nếu cần
}