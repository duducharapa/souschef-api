package com.charapadev.secondchef.dtos;

import java.util.UUID;

public record CreateItemDTO(
    UUID productId,
    int quantity
) {
}
