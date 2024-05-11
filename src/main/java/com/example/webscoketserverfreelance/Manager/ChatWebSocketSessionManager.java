package com.example.webscoketserverfreelance.Manager;

import com.example.webscoketserverfreelance.utils.WebSocketHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class ChatWebSocketSessionManager {


    Map<String, WebSocketSession> chatSocketSessionMaps = new HashMap<>();


    public void addWebSocketSession(WebSocketSession webSocketSession){

        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);
        this.chatSocketSessionMaps.put(userId,webSocketSession);
        log.info("Added Session ID to user id " +  webSocketSession.getId() +" " + userId);

    }

    public void  removeWebSocketSession(WebSocketSession webSocketSession){
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);
        this.chatSocketSessionMaps.remove(userId);

        log.info("Remove Session ID to user id " +  webSocketSession.getId(), userId);
    }

    public WebSocketSession getWebSocketSessions(String userID){
        return chatSocketSessionMaps.get(userID);
    }
}
