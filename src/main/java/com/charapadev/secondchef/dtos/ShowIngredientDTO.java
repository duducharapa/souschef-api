package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Ingredient;
import java.util.UUID;

/**
 * Record containing the visible/relevant data about an {@link Ingredient}.
 *
 * @param id The ingredient ID.
 * @param name The ingredient quantity.
 * @param quantity The ingredient quantity.
 */

public record ShowIngredientDTO(
    UUID id,
    String name,
    long quantity
) {
}
