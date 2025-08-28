package com.ukhanov.websocket_spring.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TextHandlerB extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msgInfo = message.toString();
        System.out.println(msgInfo);

        session.sendMessage(new TextMessage("Сообщение принято."));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionInfo = session.toString();
        System.out.println("Успешное открытие сессии: "+sessionInfo);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionInfo = session.toString();
        int code = status.getCode();
        String reason = status.getReason();

        System.out.println("Сессия закрылась: " + sessionInfo);
        System.out.println("Код закрытия: " + code + ", причина: " + reason);
    }
}
