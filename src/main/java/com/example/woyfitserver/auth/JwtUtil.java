package com.example.woyfitserver.auth;

import com.example.woyfitserver.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey jwtAccessSecret;
    private final long EXPIRATION_TIME = 9000000_000L;

    public JwtUtil(@Value("${jwt.secret.access}") String JWT_SECRET){
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtAccessSecret).build().parseClaimsJws(token).getBody();
    }

    public String createToken(User user){
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMonths(12).atZone(ZoneId.of("Europe/Moscow")).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("roles", user.getRoles())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
