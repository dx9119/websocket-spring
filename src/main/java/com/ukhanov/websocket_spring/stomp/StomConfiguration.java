package com.ukhanov.websocket_spring.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StomConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Указываем точку подключения для WebSocket-клиентов (или SockJS)
        registry.addEndpoint("/portfolio");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Все сообщения с destination, начинающимся на /app, направляются в методы @MessageMapping
        config.setApplicationDestinationPrefixes("/app");

        // Включаем встроенный брокер сообщений для тем /topic и /queue
        config.enableSimpleBroker("/topic", "/queue");
    }
}
