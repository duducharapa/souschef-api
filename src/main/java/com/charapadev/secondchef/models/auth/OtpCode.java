package com.charapadev.secondchef.models.auth;

import com.charapadev.secondchef.models.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the One-time Password(OTP) codes generated every time one {@link User User}
 * tries to auth on application.
 *
 * @see <a href="https://pt.wikipedia.org/wiki/Senha_descart%C3%A1vel">OTP concept</a>
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "otp_codes")
public class OtpCode {

    /**
     * OTP reference identifier.
     */
    @GeneratedValue
    @Id
    private UUID id;

    /**
     * The numeric 6-digit OTP code.
     */
    @Column
    private Integer code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OtpCode otpCode = (OtpCode) o;
        return id != null && Objects.equals(id, otpCode.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
