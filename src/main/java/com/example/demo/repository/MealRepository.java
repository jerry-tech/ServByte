package com.example.demo.repository;

import com.example.demo.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("from meal m where m.mealId = :mealId")
    Meal getResturantByMeal(@Param("mealId")Long mealId);
}
