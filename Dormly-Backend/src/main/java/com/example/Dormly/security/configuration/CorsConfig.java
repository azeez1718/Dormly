package com.example.Dormly.security.configuration;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig{

    /**
     * as the interface contains Default methods from java 8 we can override and provide implementation for whatever methods we wabt
     * the Cors allows both servers to communicate as we define the origin as allowed HTTP methods
     */


    @Bean
    public WebMvcConfigurer configurer(){
        //returns an anonymous class that implements webmvc
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") //our client side can send requests to all endpoints
                        .allowedOrigins("http://localhost:55567")
                        .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                        .allowedHeaders("*") //allow client to have request include headers like Authorizaiton which includes the token
                        .allowCredentials(true);//allow cookies
            }
        };
    }




}
