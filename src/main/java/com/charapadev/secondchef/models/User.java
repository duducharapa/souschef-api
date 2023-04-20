package com.charapadev.secondchef.models;

import com.charapadev.secondchef.models.auth.Authority;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a person that manipulate resources inside the application.
 * <p>
 * To manipulate any resource, the user must be authenticated and has enough {@link Authority Authorities}
 * that vary from resource to resource.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    /**
     * The user reference identifier.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user email.
     */
    @Column(unique = true)
    private String email;

    /**
     * The user password.
     */
    @Column
    @ToString.Exclude
    private String password;

    /**
     * The authorities this user have.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
