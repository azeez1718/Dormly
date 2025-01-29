package com.example.Dormly.jwt.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;


    /**
     * Configuration annotation tells spring that there is more than one bean that needs to be instantiated as a singleton
     * SecurityFilterChain applies a set of filters to our HTTP requests.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
        cors(Customizer.withDefaults())
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(http->http.
                        requestMatchers("api/v1/Dormly.com/Sign-up").permitAll()
                        .requestMatchers("api/v1/Dormly.com/login").permitAll()
                        .anyRequest()
                        .authenticated()
                )
        /**
         * ensure our session management remains stateless, as we authenticate once per request
         */

                        .sessionManagement(session-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                /**
                 * when spring security intercepts the login request, the usernamepasswordfilter delegates
                 * this to the auth manager in which that uses the provider manager impl to find the auth provider.
                 * The auth provider is called to validate the credentials as it receives an auth object
                 * Also ensure the jwt filter gets called before the usernamepass filter
                 * as we always need to check if a JWT is present, if not the request is passed to other filters
                 * This way we handle requests for unauthenticated and authenticated users
                 * not authenticated(meaning no jwt) -> UsernamePassFilter gets used
                 * authenticated(has Jwt) -> jwtAuthFilter
                 */

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return httpSecurity.build();


    }

    /***
     * spring security by default overrides the global cors config
     * when a request is made to a secure api, spring security intercepts it and checks if there is a cors config defined
     * the bean returns an instance of a corsconfigsource impl
     * the urlbased instance is used by spring and it invokes the getconfigurationmethod
     * the getconfig method compares the request url vs the patterns we defined and checks the headers
     * the max age allows us to cache the options request for the same origin
     * this is to prevent the server cross checking the same request again and again
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:49312"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setMaxAge(3600L);//cache requests for one hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
