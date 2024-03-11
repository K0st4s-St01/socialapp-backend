package com.kstoi.social.config.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    private static final long EXPIRATIONTIME = 8640000;
    private static final String PREFIX = "Bearer";
    private static final Key key = Keys.hmacShaKeyFor("asdzxadsA!!!".getBytes(StandardCharsets.UTF_8));

    public String getToken(String username){
        String token = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis()+EXPIRATIONTIME))
                .signWith(key)
                .compact();
        return token;
    }
    public String getAuthUser(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null){
            String user = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token.replace(PREFIX,""))
                    .getPayload()
                    .getSubject();
            if(user != null){
                return user;
            }
        }
        return null;
    }
}
