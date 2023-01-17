package com.charapadev.secondchef.dtos;

import java.util.List;

public record CreateRecipeDTO(
    String name,
    List<CreateIngredientDTO> ingredients
) {
}
