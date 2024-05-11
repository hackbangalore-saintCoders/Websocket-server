package com.example.webscoketserverfreelance.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;


public class WebSocketHelper {

    public static String userIdKey = "userID";

    public static String roomId = "roomID";

    public static String getUserIdFromSessionAttribute(WebSocketSession webSocketSession){
        return (String) webSocketSession.getAttributes().get(userIdKey);
    }

    public static String getRoomIdFroSessionAttribute(WebSocketSession webSocketSession){
        return (String) webSocketSession.getAttributes().get(roomId);
    }

    public static String getUserIdFromUrl(String url){
        return url.substring(url.lastIndexOf('/')+1);
    }

    public static String getSecondLastPartFromUrl(String url) {
        String[] parts = url.split("/");
        System.out.println(parts.toString());
        if (parts.length >= 2) {
            System.out.println(parts[parts.length - 2]);
            return parts[parts.length - 2];
        } else {

            return "";
        }
    }




}