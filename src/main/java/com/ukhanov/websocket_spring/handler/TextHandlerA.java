package com.ukhanov.websocket_spring.handler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TextHandlerA extends TextWebSocketHandler {

    // Комнаты: roomId → список сессий
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String room = (String) session.getAttributes().get("room"); //забераем из атрибута комнату к которой относится пользователь
        if (room == null) {
            session.sendMessage(new TextMessage("Вы не подключены к комнате"));
            return;
        }

        String payload = message.getPayload();
        String response = "[" + room + "] " + payload;
        //String handshakeHeaders = session.getHandshakeHeaders().toString();

        // Рассылаем всем участникам комнаты
        for (WebSocketSession s : rooms.getOrDefault(room, Collections.emptySet())) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(response));
            }
        }
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        if (uri == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        Map<String, String> params = parseQueryParams(uri.getQuery());
        String token = params.get("token");
        String room = params.get("room");

        if (!isValidToken(token)) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Неверный токен"));
            return;
        }

        if (room == null || room.isEmpty()) {
            session.close(CloseStatus.BAD_DATA.withReason("Комната не указана"));
            return;
        }

        System.out.println("Новое соединение: " + session.getId());

        // Добавляем сессию в комнату
        rooms.computeIfAbsent(
                room, //ключ, который ищем
                r -> ConcurrentHashMap.newKeySet())// значение, если ключа нет
                .add(session);

        session.getAttributes().put("room", room);

        session.sendMessage(new TextMessage("Добро пожаловать в " + room));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String room = (String) session.getAttributes().get("room");

        if (room != null) {
            rooms.getOrDefault(room, Collections.emptySet()).remove(session);
        }
        System.out.println(session.getId() + " Сессия закрыта");
    }

    private boolean isValidToken(String token) {
        // Простейшая проверка
        return true;
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;

        for (String param : query.split("&")) {
            String[] pair = param.split("=", 2);
            if (pair.length == 2) {
                String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                map.put(key, value);
                System.out.println("key,value: "+key+"|"+value);
            }

        }
        return map;
    }

}
