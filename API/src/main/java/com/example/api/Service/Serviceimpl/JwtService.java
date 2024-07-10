package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class JwtService {
private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey secretKeyrefesh = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenExpiration = 60_000000; // 1 phút
    private final long refreshTokenExpiration = 300_000000; // 5 phút

 //tao token

public  String generationtoken(User user){
    return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime()+ accessTokenExpiration))
            .signWith(secretKey)
            .compact();
}

    // setclaims để đặt các thông tin muốn đưa vào jwt , ví dụ thêm email chẳng hạn
    public  String generationrefeshtoken( User user){
        return Jwts.builder()

                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+ refreshTokenExpiration))
                .signWith(secretKeyrefesh)
                .compact();
    }
//trich USername from token

public String extractUsernamefromToken(String token){
  return   Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();


}
public String extractUsernamefromRefeshToke(String token){
    return Jwts.parserBuilder()
            .setSigningKey(secretKeyrefesh)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

}

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()

                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();


    }
    public long getExpirationTime(){
        return accessTokenExpiration;
    }



    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    public boolean validateJwtTokenrefesh(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKeyrefesh).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());}
    public boolean isTokenExpiredrefesh(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKeyrefesh)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());}
}
