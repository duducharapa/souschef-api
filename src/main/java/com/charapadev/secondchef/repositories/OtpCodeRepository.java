package com.charapadev.secondchef.repositories;

import com.charapadev.secondchef.models.auth.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {

    Optional<OtpCode> findByCode(Integer code);

}