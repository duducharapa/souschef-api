package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.stream.Stream;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    @Query(
        "SELECT case WHEN count(r) > 0 THEN true ELSE false END " +
        "FROM Recipe r WHERE r.name = :recipeName"
    )
    boolean existsByName(String recipeName);

    @Query(
        "SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i JOIN i.product p " +
        "WHERE p.id IN " +
        "(SELECT p.id FROM Item it JOIN it.product p JOIN it.user u WHERE u.email = :userEmail)"
    )
    Stream<Recipe> findRecipesPossibleForUser(String userEmail);

}
