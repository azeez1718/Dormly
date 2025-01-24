package com.example.Dormly.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * as the interface contains Default methods from java 8 we can override and provide implementation for whatever methods we wabt
     * the Cors allows both servers to communicate as we define the origin as allowed HTTP methods
     */

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**") //our client side can send requests to all endpoints
                .allowedOrigins("http://localhost:55567")
                .allowedMethods("GET", "PUT", "POST", "DELETE")
                .allowedHeaders("*") //allow client to have request include headers like Authorizaiton which includes the token
                .allowCredentials(true);//allow cookies


    }


}
