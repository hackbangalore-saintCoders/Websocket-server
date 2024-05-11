package com.example.webscoketserverfreelance.Manager;

import com.example.webscoketserverfreelance.utils.WebSocketHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
@Slf4j
public class WebRtcSocketSessionManager {

    Map<String, Set<String>> roomMap = new HashMap<>();

    // Room ID -> {UUID1, UUID2}

    Map<String, WebSocketSession> WebrtcSocketSessionMaps = new HashMap<>();

    // UUID -> SOCKET-SESSION

    public void addWebSocketSession(WebSocketSession webSocketSession){

        String roomID = WebSocketHelper.getRoomIdFroSessionAttribute(webSocketSession);
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);


        this.roomMap.computeIfAbsent(roomID, k -> new HashSet<>()).add(userId);
        this.WebrtcSocketSessionMaps.put(userId,webSocketSession);

        log.info("Added Room ID to user id" + roomID + webSocketSession.getId());
        log.info("Added Session ID to user id " +  webSocketSession.getId() +" " + userId);

    }

    public void  removeWebSocketSession(WebSocketSession webSocketSession){

        String roomID = WebSocketHelper.getRoomIdFroSessionAttribute(webSocketSession);
        String userId = WebSocketHelper.getUserIdFromSessionAttribute(webSocketSession);

        this.roomMap.get(roomID).remove(userId);
        this.WebrtcSocketSessionMaps.remove(userId);
        log.info("Remove Session ID to user id " +  webSocketSession.getId(), userId);
    }

    public WebSocketSession getWebSocketSessions(String roomID, String senderID){

        Set<String> userIds = roomMap.get(roomID);

        String clientId = null;

        for( String uuid : userIds){
            if(!uuid.equals(senderID)){
                clientId = uuid;
                break;
            }
        }

        return WebrtcSocketSessionMaps.get(clientId);
    }
}
