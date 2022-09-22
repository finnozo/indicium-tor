package com.lov4code.security;

import com.lov4code.exception.GeneralException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;

import static com.lov4code.util.AppConstants.*;

@Service
@Slf4j
public class JwtProviderImpl implements JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance(INSTANCE_TYPE);
            InputStream resourceAsStream = getClass().getResourceAsStream(PRIVATE_KEY_PATH);
            keyStore.load(resourceAsStream, KEY_PASSWORD);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new GeneralException("Exception occurred while loading keystore");
        }

    }

    @Override
    public String generateToken(Authentication authentication) {
        Date now = new Date();
        long jwtExpirationInMs = TOKEN_EXPIRY;
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setIssuedAt(new Date())
                .setIssuer(ISSUER_ALIAS)
                .setExpiration(expiryDate)
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(ISSUER_ALIAS, KEY_PASSWORD);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new GeneralException("Exception occurred while retrieving public key from keystore");
        }
    }

    @Override
    public boolean validateToken(String jwt) {
        try {
            getAllClaimsFromToken(jwt);
            return true;
        } catch (MalformedJwtException | SignatureException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(ISSUER_ALIAS).getPublicKey();
        } catch (KeyStoreException e) {
            throw new GeneralException("Exception occurred while retrieving public key from keystore");
        }
    }

    @Override
    public String getUsernameFromJWT(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getPublicKey())
                .build().parseClaimsJws(token).getBody();
    }
}
