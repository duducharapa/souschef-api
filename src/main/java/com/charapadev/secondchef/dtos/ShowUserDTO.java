package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.User;
import java.util.UUID;

/**
 * Record containing the visible/relevant data about an  {@link User}.
 *
 * @param id The user ID.
 * @param email The user email address.
 */

public record ShowUserDTO(
    UUID id,
    String email
) {
}
