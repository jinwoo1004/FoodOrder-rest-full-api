package com.kr.foodorder.service;

import com.kr.foodorder.domain.Food;
import com.kr.foodorder.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Optional<Food> getFood(Long id) {
        return foodRepository.findById(id);
    }

    public List<Food> searchFoods(String keyword) {
        return foodRepository.findByNameContaining(keyword);
    }

    public List<Food> findByCategory(String category) {
        return foodRepository.findByCategory(category);
    }

    public Food updateFood(Food food) {
        return foodRepository.save(food);
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}