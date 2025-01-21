package com.example.Dormly.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /**
         * the first thing we want to do is check if a jwt token is present
         * Hence we need to access the header, which will allow us access the token
         * we need to ensure the user is sending a valid jwt
         */

        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            /**
             * we still allow for other filters to be checked, assuming the client
             * is logging in (which permits authentication) then they will not have a jwt initially
             * unless given to them.
             * The FilterSecurityInterceptor still needs to be accessed if a token is not present, it checks whether the endpoint is permitted or restricted
             */
            filterChain.doFilter(request,response);
        }
        //the token is now present
        //At the 7th index is where the token begins after 'Bearer'
        String jwt = authHeader.substring(7);







    }
}
