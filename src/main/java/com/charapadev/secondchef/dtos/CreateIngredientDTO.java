package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Ingredient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Record containing the data accepted to create a new {@link Ingredient Ingredient}.
 * @param name The ingredient name.
 * @param quantity The ingredient quantity necessary.
 */

public record CreateIngredientDTO(
    @NotBlank String name,
    @PositiveOrZero @NotNull Long quantity
) {
}
