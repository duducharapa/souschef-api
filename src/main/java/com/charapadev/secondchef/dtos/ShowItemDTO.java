package com.charapadev.secondchef.dtos;

import java.util.UUID;

public record ShowItemDTO(
    UUID id,

    String name,

    int quantity
) {
}
