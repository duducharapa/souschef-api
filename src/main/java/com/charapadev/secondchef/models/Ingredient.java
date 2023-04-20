package com.charapadev.secondchef.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents one {@link Product} necessary to cook a {@link Recipe}.
 * <p>
 * This class works as an intermediary between recipes and products.
 * <p>
 * Instead a recipe depends on directly from a product, the ingredients are created to specify values like
 * quantity and measures.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ingredients")
public class Ingredient {

    /**
     * The ingredient reference identifier.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The quantity of the ingredient to fulfill a specific recipe.
     */
    @Column
    private long quantity;

    /**
     * The recipe related to this ingredient.
     */
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    /**
     * The specific product related to this ingredient.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
