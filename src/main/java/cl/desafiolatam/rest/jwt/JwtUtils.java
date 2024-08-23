package cl.desafiolatam.rest.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtUtils implements Serializable {
    private static final long serialVersionUID = 342414840687873249L;
    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    public String getJwtDetailsFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }

    public String generateToken(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return Jwts.builder()
                   .setSubject(userName)
                   .signWith(getSigningKey())
                   .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationInMs)))
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .compact();
    }

    private Key getSigningKey() {
        // Use the securely generated key
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUserNameFromJwtToken(String jwtToken) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(jwtToken)
                   .getBody()
                   .getSubject();
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            logger.info("Validating Token: " + jwtToken);
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException me) {
            logger.log(Level.SEVERE, "JWT Token {0} is invalid", jwtToken);
        } catch (ExpiredJwtException jex) {
            logger.log(Level.SEVERE, "JWT Token {0} is expired", jwtToken);
        } catch (UnsupportedJwtException jue) {
            logger.log(Level.SEVERE, "JWT Token {0} is not supported", jwtToken);
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, "JWT claims is empty");
        }
        return false;
    }
}
