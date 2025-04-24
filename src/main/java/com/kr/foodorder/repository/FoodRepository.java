package com.kr.foodorder.repository;

import com.kr.foodorder.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
    List<Food> findByNameContaining(String name);
}