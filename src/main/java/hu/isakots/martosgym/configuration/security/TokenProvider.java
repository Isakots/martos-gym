package hu.isakots.martosgym.configuration.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class.getName());
    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private final String jwtType;
    private final int jwtExpirationInMs;

    public TokenProvider(@Value("${jwt.secretKey}") String secretKey,
                            @Value("${jwt.expirationInMs}") int jwtExpirationInMs,
                            @Value("${jwt.jwtType}") String jwtType) {
        this.secretKey = secretKey;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.jwtType = jwtType;
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        Calendar expiration = Calendar.getInstance();
        expiration.setTimeInMillis(new Date().getTime() + jwtExpirationInMs);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(jwtType);
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(signatureAlgorithm, secretKey)
            .setExpiration(expiration.getTime())
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            LOGGER.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            LOGGER.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            LOGGER.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            LOGGER.info("JWT token compact of handler are invalid.");
        }
        return false;
    }
}