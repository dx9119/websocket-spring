package com.ukhanov.websocket_spring.sockjs;

import com.ukhanov.websocket_spring.ws.handler.TextHandlerA;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class SockJsConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handlerDefault(), "/handler-sock-js")
                .setAllowedOrigins("https://app.local:8443")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS()
                .setHeartbeatTime(15000)
                .setTaskScheduler(sockJsTaskScheduler());
    }

    // Настройка TaskScheduler
    @Bean
    public ThreadPoolTaskScheduler sockJsTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        scheduler.setThreadNamePrefix("SockJS-Heartbeat-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    public WebSocketHandler handlerDefault() {
        return new TextHandlerA();
    }

}
