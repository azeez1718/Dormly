package com.example.Dormly.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class JwtService {

    private final String SIGN_IN_KEY = "";

    public String extractSubject(String jwt) {
        String userEmail = extractClaim(jwt, Claims::getSubject);
        return userEmail;

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

    private byte[] getSignKey() {

    }


}
