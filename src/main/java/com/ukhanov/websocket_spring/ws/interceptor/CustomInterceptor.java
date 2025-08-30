package com.ukhanov.websocket_spring.ws.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CustomInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        URI uri = request.getURI();
        String query = uri.getQuery();

        Map<String, String> params = parseQueryParams(query);
        String token = params.get("token");

        //можно контролировать и заголовки
        if (!isValidToken(token)) {
            System.out.println("Неверный токен: " + token);
            return false;
        }

        attributes.put("token", token); // передаём в WebSocketSession
        attributes.put("room", params.get("room"));

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }


    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;
        for (String param : query.split("&")) {
            String[] pair = param.split("=", 2);
            if (pair.length == 2) {
                map.put(pair[0], URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }

    private boolean isValidToken(String token) {
        return true;
    }
}
