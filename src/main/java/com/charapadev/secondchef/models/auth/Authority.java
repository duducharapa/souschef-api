package com.charapadev.secondchef.models.auth;

import com.charapadev.secondchef.models.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the authority concept based on Spring Security, where the {@link User User} has a lot of privileges
 * and your access to some resources or actions are based on it.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "authorities")
public class Authority {

    /**
     * The authority reference identifier.
     */
    @GeneratedValue
    @Id
    private UUID id;

    /**
     * The authority name.
     */
    @Column
    private String name;

    /**
     * The user that has this authority.
     */
    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authority authority = (Authority) o;
        return id != null && Objects.equals(id, authority.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
