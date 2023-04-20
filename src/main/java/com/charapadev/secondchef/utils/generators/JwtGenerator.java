package com.charapadev.secondchef.utils.generators;

import com.charapadev.secondchef.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Utility class that generates a JWT token used for {@link User Users} to
 * be recognized on application.
 */

@Component
public class JwtGenerator {

    @Value("${jwt.key}")
    private String jwtKey;

    /**
     * Generates a valid JWS using the SHA512 algorithm.
     *
     * @param username The username to register on token.
     * @return A valid JWS.
     */
    public String generate(String username) {
        SecretKey key = Keys.hmacShaKeyFor(
            jwtKey.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
            .setClaims(Map.of("username" , username))
            .signWith(key)
            .compact();
    }

}
