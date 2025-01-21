package com.example.Dormly.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * the authentication provider is what authenticates the user once we set up the auth object
     * the jwt only validates the token, and checks that the subject exists in the database
     * the rest is handled by the authentication provider
     * The Bean annotation allows spring to invoke the method and return its instance
     * the instance returned will be injected into any component that requires it
     *The dao provider always call the matches function in the Bcrypt class
     */


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;


    }


    /**
     * Spring security returns a default impl of the Auth manager which is the provider manager
     * the provider manager delegates the auth object to the authentication provider
     * use the configuration to return the default manager which is provider manager
     * the authentication manager contains instances of the auth provider (DAO)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        authenticationConfiguration.getAuthenticationManager();

    }

    /**
     * BcryptEncoder is an impl of the password encoder that is used to hash the incoming password
     * @return BcryptEncoder instance which will be used by the provider
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
