package com.charapadev.secondchef.dtos;

import java.util.UUID;

public record ShowUserDTO(
    UUID id,
    String email
) {
}
