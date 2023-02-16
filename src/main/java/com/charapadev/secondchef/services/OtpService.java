package com.charapadev.secondchef.services;

import com.charapadev.secondchef.models.auth.OtpCode;
import com.charapadev.secondchef.repositories.OtpCodeRepository;
import com.charapadev.secondchef.utils.generators.OtpCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j(topic = "Otp service")
public class OtpService {

    @Autowired
    private OtpCodeRepository otpCodeRepository;

    public void create() {
        Integer generatedCode = OtpCodeGenerator.generate();

        OtpCode codeToCreate = OtpCode.builder()
            .code(generatedCode)
            .build();

        otpCodeRepository.save(codeToCreate);
        log.info("Generated a new OTP code: {}", codeToCreate);
    }

    public Optional<OtpCode> findOne(Integer code) {
        return otpCodeRepository.findByCode(code);
    }

}
