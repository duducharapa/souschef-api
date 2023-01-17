package com.charapadev.secondchef.dtos;

import java.util.UUID;

public record ShowIngredientDTO(
    UUID id,
    String name,
    long quantity
) {
}
