package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Ingredient;
import com.charapadev.secondchef.models.Recipe;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Record containing the data accepted to create a new {@link Recipe}.
 *
 * @param name The recipe name.
 * @param ingredients The list of {@link Ingredient Ingredients} contained.
 */

public record CreateRecipeDTO(
    @NotBlank String name,
    @Valid @Length(min = 1) List<CreateIngredientDTO> ingredients
) {
}
