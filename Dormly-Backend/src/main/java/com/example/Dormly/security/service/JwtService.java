package com.example.Dormly.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SIGN_IN_KEY;

    public String extractSubject(String jwt) {
        String userEmail = extractClaim(jwt, Claims::getSubject);
        return userEmail;
        /**
         * alternative
         *    Claims claim = extractAllClaims(jwt);
         *    return claims.getSubject();
         *    however extract claim function makes it easier to return different things in the claims
         */

    }

    /**
     *
     * @param userDetails extract the username to set as subject
     * @param extraClaims additional data in the claims like roles, which help with fetching roles and permissions
     * set the expiration of the token to 24 hours.
     * @return a jwt token specific for the user
     */


    public String generateToken(UserDetails userDetails, HashMap<String, Object>extraClaims){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * another method incase we dont want to add claims
     */

    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, new HashMap<>());
    }

    private <T>T extractClaim(String jwt, Function<Claims, T> SpecificClaims){
        Claims claim = extractAllClaims(jwt);
        /**
         * we can apply a lambda like method on the claims object to retrieve specific things in the payload
         * calling extractClaim(jwt, Claim::getExpiration) allows us to retrieve anything from the claims
         * kinda a helper method
         */
        return SpecificClaims.apply(claim);
    }


    /**
     * The jwt token extracted from the Request is used.
     * This uses our SignInKey to regenerate the signature using the header and payload before extracting claims
     * if the re-generated signature matches the one of the request, we can extract the claims from the jwt
     */
    private Claims extractAllClaims(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignKey() {
        /**
         * Cryptographic algorithms expect keys to be in binary format
         * the string was Base64 encoded meaning we encoded it from binary to string
         * hence we need to decode this back to binary as SHA256 only works with binary data
         * hence we hash the key and return it as a key type
         */
        byte[] key = Decoders.BASE64.decode(SIGN_IN_KEY);
        return Keys.hmacShaKeyFor(key);


    }

    public Boolean isTokenValid(String jwt, UserDetails userDetails){
        String jwtSubject = extractSubject(jwt);
        String userEmail =  userDetails.getUsername();

        return jwtSubject.equals(userEmail) && !isTokenExpired(jwt);
    }

    public Boolean isTokenExpired(String jwt){
        /**
         * we want to see that the token has not yet been expired
         * if the expiry date is before the current date then it is an invalid token
         */
        Date expirationDate = extractClaim(jwt, Claims::getExpiration);
        return expirationDate.before(new Date());

    }



}
