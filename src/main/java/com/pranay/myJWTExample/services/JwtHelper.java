package com.pranay.myJWTExample.services;

import com.pranay.myJWTExample.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    public static final Long JWT_TOKEN_VALIDITY = 5*60*60L;
    private String secret = AppConstants.SECRET_KEY;


    // Retrieving information from token , we need secret key
    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Getting
    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieving username from jwt token
    public String getUserNameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieving ExpirationDate from jwt token
    public Date getExpirationDateFrom(String token)
    {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFrom(token);
        return expiration.before(new Date());
    }

    // Validate Token
    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    // Generate Jwt Token Utility Method
    private String doGenerateToken(Map<String, Object> claims, String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public String generateToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

}
