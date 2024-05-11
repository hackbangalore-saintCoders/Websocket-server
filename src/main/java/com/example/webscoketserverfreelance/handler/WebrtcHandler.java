package com.example.webscoketserverfreelance.handler;

import com.example.webscoketserverfreelance.Manager.ChatWebSocketSessionManager;
import com.example.webscoketserverfreelance.Manager.WebRtcSocketSessionManager;
import com.example.webscoketserverfreelance.utils.WebSocketHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;


@Component
public class WebrtcHandler extends TextWebSocketHandler {

    @Autowired
    WebRtcSocketSessionManager webSocketSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionManager.addWebSocketSession(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        System.out.println(message);
        String payload = message.getPayload();

        Map<String,String> map = new HashMap<>();
        map.put("message", payload);

        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the map to a JSON string
        String json = objectMapper.writeValueAsString(map);

        webSocketSessionManager
                .getWebSocketSessions(WebSocketHelper.getRoomIdFroSessionAttribute(session), WebSocketHelper.getUserIdFromSessionAttribute(session)).sendMessage(new TextMessage(json));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionManager.removeWebSocketSession(session);
        super.afterConnectionClosed(session, status);
    }
}
