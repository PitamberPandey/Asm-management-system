package com.amsmanagament.system.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger =
            LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/topic", "/queue");

        config.setApplicationDestinationPrefixes("/app");

        config.setUserDestinationPrefix("/user");

        logger.info("WebSocket broker configured");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")

                // 🔥 IMPORTANT FIX (CORS for React frontend)
                .setAllowedOriginPatterns("*")

                // 🔥 IMPORTANT FIX (SockJS fallback support)
                .withSockJS();

        logger.info("WebSocket endpoint /ws registered");
    }
}