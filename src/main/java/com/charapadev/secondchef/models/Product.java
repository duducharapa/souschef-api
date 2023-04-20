package com.charapadev.secondchef.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the generic link between {@link Item Items} with {@link Ingredient Ingredients}.
 * <p>
 * These two classes refers to same "behavior" in real world: the items are the products which user already has in your house.
 * The ingredients are products which recipe requires to be cooked.
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    /**
     * The product reference identifier.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The product name.
     * <p>
     * This name will be extended to items and ingredients.
     */
    @Column
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
