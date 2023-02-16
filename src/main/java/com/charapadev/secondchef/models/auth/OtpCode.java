package com.charapadev.secondchef.models.auth;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "otp_codes")
public class OtpCode {

    @GeneratedValue
    @Id
    private UUID id;

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
