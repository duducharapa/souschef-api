package com.charapadev.secondchef.dtos;

import com.charapadev.secondchef.models.Item;

import javax.validation.constraints.PositiveOrZero;

/**
 * Record containing the data accepted to change a existing {@link Item}.
 *
 * @param quantity The new item quantity.
 */

public record UpdateItemDTO(
    @PositiveOrZero int quantity
) {
}
