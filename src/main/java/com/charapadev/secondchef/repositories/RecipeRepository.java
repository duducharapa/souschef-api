package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
