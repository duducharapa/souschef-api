package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
