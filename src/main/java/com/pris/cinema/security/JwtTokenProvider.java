package com.pris.cinema.security;

import com.pris.cinema.entities.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.pris.cinema.security.SecurityConstants.EXPIRATION_TIME;
import static com.pris.cinema.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    // generate the token
    public String generateToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", userId);
        claims.put("username", user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // validate the token
    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;

        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");
        }

        return false;
    }

    // get user id from the token
    public Long getUserIdFromJwt(String token) {

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        String idString = (String) claims.get("id");
        Long id = Long.parseLong(idString);

        return id;
    }
}
