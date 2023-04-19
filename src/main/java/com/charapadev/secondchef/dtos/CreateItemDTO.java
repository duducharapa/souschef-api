package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

/**
 * Record containing the data accepted to create a new {@link Item}.
 *
 * @param productId The {@link Product} reference ID.
 * @param quantity The item quantity.
 */

public record CreateItemDTO(
    @NotNull UUID productId,
    @PositiveOrZero @NotNull Integer quantity
) {
}
