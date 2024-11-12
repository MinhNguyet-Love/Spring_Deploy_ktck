package com.example.shopweb.repository;

import com.example.shopweb.model.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    // You can add custom queries here if needed
}
