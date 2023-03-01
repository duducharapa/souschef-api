package com.charapadev.secondchef.services;

import com.charapadev.secondchef.models.auth.OtpCode;
import com.charapadev.secondchef.repositories.OtpCodeRepository;
import com.charapadev.secondchef.configs.security.providers.OtpAuthenticationProvider;
import com.charapadev.secondchef.utils.generators.OtpCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service used to generate and manipulate the OTPs(One Time Password) used as second authentication step.
 *
 * @see OtpCodeGenerator Otp Generator.
 * @see OtpAuthenticationProvider Otp authentication provider.
 */

@Service
@Slf4j(topic = "Otp service")
@Transactional
public class OtpService {

    @Autowired
    private OtpCodeRepository otpCodeRepository;

    /**
     * Generates a random {@link OtpCode OTP code}.
     */
    public void create() {
        Integer generatedCode = OtpCodeGenerator.generate();

        OtpCode codeToCreate = OtpCode.builder()
            .code(generatedCode)
            .build();

        otpCodeRepository.save(codeToCreate);
        log.info("Generated a new OTP code: {}", codeToCreate);
    }

    /**
     * Searches an unique {@link OtpCode OTP code} using the given code.
     * <p>
     * The code is always unique. So, if some data is retrieved, it will be unique.
     *
     * @param code The code provided.
     * @return The code inside an Optional instance.
     *
     * @see Optional Optional specification.
     */
    public Optional<OtpCode> findOne(Integer code) {
        return otpCodeRepository.findByCode(code);
    }

}
