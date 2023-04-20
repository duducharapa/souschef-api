package com.charapadev.secondchef.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents the options offered for {@link User} to be cooked.
 * <p>
 * To a recipe can be cooked, the user should satisfy all the {@link Ingredient Ingredients} requirements.
 * <p>
 * This entity not have a direct link with the user, but using the {@link Item Items}, it becomes possible.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "recipes")
public class Recipe {

    /**
     * The recipe reference identifier.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The recipe name.
     */
    @Column(unique = true)
    private String name;

    /**
     * The ingredients that belongs this recipe.
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
