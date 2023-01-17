package com.charapadev.secondchef.dtos;

import java.util.List;
import java.util.UUID;

public record ShowRecipeDTO(
    UUID id,
    String name,
    List<ShowIngredientDTO> ingredients
) {
}
