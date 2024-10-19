package com.example.shopweb.repository;

import com.example.shopweb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Rename to ProductRepository
public interface ProductRepository extends JpaRepository<Product, Long> {
}