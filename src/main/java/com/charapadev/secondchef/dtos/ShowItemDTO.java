package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Item;
import com.charapadev.secondchef.models.Product;
import java.util.UUID;

/**
 * Record containing the visible/relevant data about an {@link Item}.
 *
 * @param id The item ID.
 * @param name The {@link Product} related name.
 * @param quantity The item quantity.
 */

public record ShowItemDTO(
    UUID id,
    String name,
    long quantity
) {
}
