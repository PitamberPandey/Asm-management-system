package com.amsmanagament.system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Correct SLF4J Logger (no casting!)
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // frontend listens here
        config.setApplicationDestinationPrefixes("/app");
        logger.info("Message broker configured with /topic and /app prefixes");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*") // allow all origins
                .withSockJS();          // enable SockJS fallback
        logger.info("WebSocket endpoint /ws registered with SockJS support");
    }
}