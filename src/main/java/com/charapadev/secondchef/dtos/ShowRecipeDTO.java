package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Recipe;
import java.util.List;
import java.util.UUID;

/**
 * Record containing the visible/relevant data about an {@link Recipe}.
 *
 * @param id The recipe ID.
 * @param name The recipe name.
 * @param ingredients The list of {@link Ingredient Ingredients} related.
 */

public record ShowRecipeDTO(
    UUID id,
    String name,
    List<ShowIngredientDTO> ingredients
) {
}
