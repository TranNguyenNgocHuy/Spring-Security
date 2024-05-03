package com.example.DemoSecurity.security.jwt;


import com.example.DemoSecurity.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    private final String SECRET_KEY = "ae74d2bb9224e6a3ecd56b26b76d05a20361d21289e9a5b0553c0c7cc2e0272c";
    private final Long JWT_EXPIRATION = 86400000L; // 24h * 60m * 60s * 1000

    // Method extract Token form Http Servlet
    public String getToken (HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7,bearerToken.length()); } // The part after "Bearer "
        return null;
    }

    /**
     * @Title: Extract all Claims from token
     *
     * Method basic:
     * public Claims extractAllClaims(String token) {
     *     return Jwts.parser()
     *     .setSigningKey(SECRET_KEY)
     *     .parseClaimsJws(token)
     *     .getBody();
     * }
     *
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Create Signing Key
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract User Name from token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Extract the Object information
    // from Token and function Claims extractAllClaims
    // Ex:
    // Integer userId = extractClaim(token, claims -> claims.get("userId", Integer.class));
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // Extract Expiration time from token (**)
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Then base on (**) and current time check isTokenExp
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kiểm tra Username(dự án lấy email làm username)
    // trong Token có khớp ko và token còn hạn ko
    public boolean isValid(String token, UserDetails user) {
        String email = extractUserName(token);
        return email.equals(user.getUsername()) && !isTokenExpired(token);
    }

    // Create Token
    public String generateToken(User user) {
        String token = Jwts
                // Bắt đầu xây chuỗi Jwt
                .builder()
                // get User name
                .setSubject(user.getUsername())
                // create a claim with(String, List<String> roles)
                .claim("role", user.getRoles())
                // set create time
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // set Expiration time
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                // set signature
                .signWith(getSigningKey())
                // completed
                .compact();

        return token;
    }

    // Method validate Jwt by SECRET_KEY
    public boolean validateToken(String token)  {
        try { Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
