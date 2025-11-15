package com.cts.paymentService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class JwtUtil {
    @Value("${key}")
    private String secretKey;
    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUserName(String token){
        return extractClaims(token).getSubject();
    }
    public List<String> extractRoles(String token){
        return extractClaims(token).get("roles",List.class);
    }


}