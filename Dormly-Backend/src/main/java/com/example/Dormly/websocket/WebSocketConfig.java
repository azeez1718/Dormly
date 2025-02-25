package com.example.Dormly.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

     /// this method initiates the web socket connection, when a client wants to upgrade their
    /// protocol from HTTP to WebSocket
        /// . we also define the servers that can make initiate a websocket connection
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:49322")
                .withSockJS();
    }

    /// we define the prefix of the placeholder in the url as 'app' in which this will be binded to the
    /// MessageMapping annotation methods, similar to how requestMapping routed the endpoint to the specific method
    /// the message broker is used to define the endpoint in which a user will be subscribed to
    /// we set the user as the prefix
    ///
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
