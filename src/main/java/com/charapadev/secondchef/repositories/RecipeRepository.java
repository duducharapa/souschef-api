package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    @Query(
        "SELECT case WHEN count(r) > 0 THEN true ELSE false END " +
        "FROM Recipe r WHERE r.name = :recipeName"
    )
    boolean existsByName(String recipeName);

}
