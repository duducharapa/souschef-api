package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;
import com.charapadev.secondchef.models.Recipe;
import com.charapadev.secondchef.models.User;
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

    /**
     * Searches the possible {@link Recipe Recipes} to a specific {@link User} cook.
     * <p>
     * To be considered, at least, possible to cook, the user must have {@link Item Items} satisfying
     * the {@link Ingredient Ingredients} link by same {@link Product}.
     *
     * @param userEmail The user email.
     * @return The stream containing the recipes.
     */
    @Query(
        "SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i JOIN i.product p " +
        "WHERE p.id IN " +
        "(SELECT p.id FROM Item it JOIN it.product p JOIN it.user u WHERE u.email = :userEmail)"
    )
    Stream<Recipe> findRecipesPossibleForUser(String userEmail);

}
