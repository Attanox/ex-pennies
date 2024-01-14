package com.andi.expennies;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final byte[] API_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
}
