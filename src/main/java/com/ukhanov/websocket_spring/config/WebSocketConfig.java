package com.ukhanov.websocket_spring.config;

import com.ukhanov.websocket_spring.interceptor.CustomInterceptor;
import com.ukhanov.websocket_spring.handler.TextHandlerA;
import com.ukhanov.websocket_spring.handler.TextHandlerB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/handler-a")
                .addInterceptors(new HttpSessionHandshakeInterceptor()) //default interceptor
                .setAllowedOrigins("*");

        registry.addHandler(handlerWithCustom(), "/handler-b")
                .addInterceptors(new CustomInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new TextHandlerA();
    }

    @Bean
    public WebSocketHandler handlerWithCustom(){
        return new TextHandlerB();
    }
}
