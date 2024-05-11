package com.example.webscoketserverfreelance.handler;

import com.example.webscoketserverfreelance.Manager.ChatWebSocketSessionManager;
import com.example.webscoketserverfreelance.utils.WebSocketHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;


@Component

public class ChatHandler extends TextWebSocketHandler {

    /**
     *
     *
     * from
     * to
     * message
     *
     *
     */


    @Autowired
    ChatWebSocketSessionManager webSocketSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionManager.addWebSocketSession(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        System.out.println(message);
        String payload = message.getPayload();
        String[] payLoadSplit = payload.split("/");
        String targetUserId  = payLoadSplit[0].trim();
        String messageToBeSent = payLoadSplit[1].trim();
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(session);

        Map<String,String> map = new HashMap<>();
        map.put("sentBy",userId);
        map.put("message", messageToBeSent);
        map.put("target",targetUserId);

        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the map to a JSON string
        String json = objectMapper.writeValueAsString(map);


        System.out.println(targetUserId);


        webSocketSessionManager.getWebSocketSessions(targetUserId).sendMessage(new TextMessage(json));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionManager.removeWebSocketSession(session);
        super.afterConnectionClosed(session, status);
    }

}
