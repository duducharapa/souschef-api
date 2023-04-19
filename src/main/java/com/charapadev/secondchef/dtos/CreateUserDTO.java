package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.User;
import javax.validation.constraints.NotBlank;

/**
 * Record containing the data accepted to create a new {@link User}.
 *
 * @param email The user email.
 * @param password The user password.
 */

public record CreateUserDTO(
    @NotBlank String email,
    @NotBlank String password
) {
}
