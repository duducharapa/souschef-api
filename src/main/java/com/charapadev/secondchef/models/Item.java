package com.charapadev.secondchef.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the {@link Product} owned by a specific {@link User}.
 * <p>
 * Like {@link Ingredient} entity, this also works as an intermediary and has the same "specific values" reason to exist.
 * <p>
 * See the ingredient reference to know about.
 *
 * @see Ingredient Ingredient reference.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "items")
public class Item {

    /**
     * The item reference identifier.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The quantity of this item owner by user.
     */
    @Column
    private long quantity;

    /**
     * The user that owns this item.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The product that belongs this item.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
