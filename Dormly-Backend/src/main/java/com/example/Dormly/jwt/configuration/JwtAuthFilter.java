package com.example.Dormly.jwt.configuration;

import com.example.Dormly.jwt.service.CustomUserDetailsService;
import com.example.Dormly.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        /**
         * the first thing we want to do is check if a jwt token is present
         * Hence we need to access the header, which will allow us access the token
         * we need to ensure the user is sending a valid jwt
         */

        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            /**
             * we still allow for other filters to be checked, assuming the client
             * is logging in (which permits authentication) then they will not have a jwt initially
             * unless given to them.
             * The FilterSecurityInterceptor still needs to be accessed if a token is not present, it checks whether the endpoint is permitted or restricted
             */
            filterChain.doFilter(request,response);
            return;
        }
        //the token is now present
        //At the 7th index is where the token begins after 'Bearer'

        if(authHeader.substring(9).equals("")){
            throw new RuntimeException("the token we received is not of the right format");
        }
        String jwt = authHeader.substring(7);


        /**
         * extract claims from the Jwt to access the username
         * call the UserDetailsService to check if the user exists and return a UserDetails object
         */
        String userEmail = jwtService.extractSubject(jwt);
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            /**
             *we ensure the context is null because on every request the context will be updated after checking the token
             * this is due to the jwt being stateless and hence context will always be null when sending request
             *validate the token to ensure it's not expired and that the user who sent the request
             *is the same user that we have just fetched from the database.
             */
            if(jwtService.isTokenValid(jwt,userDetails)){
                //we can now set up an authentication object for the user
                UsernamePasswordAuthenticationToken authenticatedUser= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                //essentially we are saying here that the auth object is associated to this request
                authenticatedUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //set the security context
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

            }
            //pass the request and response to the next set of filters
            filterChain.doFilter(request,response);
        }

    }
}
