package com.charapadev.secondchef.utils.generators;

import com.charapadev.secondchef.configs.security.providers.OtpAuthenticationProvider;
import com.charapadev.secondchef.models.auth.OtpCode;
import java.util.Random;

/**
 * Utility class that generates the {@link OtpCode#code} used on two steps authentication.
 * <p>
 * See the authentication provider responsible for otp codes.
 *
 * @see OtpAuthenticationProvider Otp Authentication Provider.
 */

public class OtpCodeGenerator {

    /**
     * Generates a 6 digit code used on {@link OtpCode}.
     *
     * @return The code generated.
     */
    public static Integer generate() {
        Random random = new Random();

        // This sum guarantee the code has exactly 6 digits.
        // THe number starts at 100000 and ends in 999999.
        return 100000 + random.nextInt(900000);
    }

}
