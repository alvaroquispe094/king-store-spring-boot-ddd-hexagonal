package com.groupal.king.store.adapter.security;

import com.groupal.king.store.domain.UserDetail;
import com.groupal.king.store.application.port.out.JwtUtilsRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtilsAdapter implements JwtUtilsRepository {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtilsAdapter.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Override
    public String generateJwtToken(UserDetail userPrincipal) {
      return generateTokenFromUsername(userPrincipal.getUsername());
    }

    @Override
    public String generateTokenFromUsername(String username) {
      return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    @Override
    public String getUserNameFromJwtToken(String token) {
      return Jwts.parserBuilder()
              .setSigningKey(getSignInKey())
              .build().parseClaimsJws(token).getBody().getSubject();
    }

    private Key getSignInKey() {
        var keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean validateJwtToken(String authToken) {
      try {
          Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parse(authToken);
        return true;
      } catch (MalformedJwtException e) {
        logger.error("Invalid JWT token: {}", e.getMessage());
      } catch (ExpiredJwtException e) {
        logger.error("JWT token is expired: {}", e.getMessage());
      } catch (UnsupportedJwtException e) {
        logger.error("JWT token is unsupported: {}", e.getMessage());
      } catch (IllegalArgumentException e) {
        logger.error("JWT claims string is empty: {}", e.getMessage());
      }

      return false;
    }

}
