package com.lov4code.security;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public interface JwtProvider {

    String generateToken(Authentication authentication);

    String getUsernameFromJWT(String token);

    boolean validateToken(String jwt);
}
