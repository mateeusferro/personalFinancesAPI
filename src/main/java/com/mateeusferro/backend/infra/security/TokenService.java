package com.mateeusferro.backend.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mateeusferro.backend.dtos.TokensDTO;
import com.mateeusferro.backend.models.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String extractUsername(String token) {
        return extractClaim(token, DecodedJWT::getSubject);
    }

    public <T> T extractClaim(String token, Function<DecodedJWT, T> claimsResolver) {
        try {
            final DecodedJWT decodedJWT = decodeToken(token);

            if (isTokenExpired(decodedJWT)){
                throw new RuntimeException("Your token has expired");
            }

            return claimsResolver.apply(decodedJWT);
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("Error decoding JWT token", e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        var decodedToken = decodeToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(decodedToken);
    }


    private DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        Date expiration = decodedJWT.getExpiresAt();
        if (expiration != null) {
            return expiration.before(new Date());
        }
        return false;
    }

    public TokensDTO generateTokens(Users user) {
        String accessToken = generateToken(user);
        String refreshToken = generateRefreshToken(user);
        return new TokensDTO(accessToken, refreshToken);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("personalFinancesAPI")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

    private String generateToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("personalFinancesAPI")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating token", ex);
        }
    }

    private String generateRefreshToken(Users user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("personalFinancesAPI")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genRefreshTokenExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating refresh token", ex);
        }
    }

    private Instant genExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genRefreshTokenExpirationTime() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00")); // Refresh token expires in 7 days, adjust as needed
    }
}
