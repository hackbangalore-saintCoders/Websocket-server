package com.example.webscoketserverfreelance.config;

import com.example.webscoketserverfreelance.handler.ChatHandler;
import com.example.webscoketserverfreelance.handler.WebrtcHandler;
import com.example.webscoketserverfreelance.utils.WebSocketHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


@Configuration
@EnableWebSocket
public class Config implements WebSocketConfigurer {

    @Autowired
    ChatHandler chatHandler;

    @Autowired
    WebrtcHandler webrtcHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(chatHandler,"/chat-socket/*")
                .addInterceptors(getParametersInterceptors())
                .setAllowedOrigins("*");

        registry.addHandler(webrtcHandler, "/webrtc-socket/**")
                .addInterceptors(getParameterWebrtcInterceptors())
                .setAllowedOrigins("*");;

    }



    @Bean
    public HandshakeInterceptor getParametersInterceptors() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                String path = request.getURI().getPath();
                String userId = WebSocketHelper.getUserIdFromUrl(path);
                attributes.put(WebSocketHelper.userIdKey, userId);
                return true;
            }

            @Override
            public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }

    @Bean
    public HandshakeInterceptor getParameterWebrtcInterceptors() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                String path = request.getURI().getPath();
                String userId = WebSocketHelper.getUserIdFromUrl(path);
                String roomID = WebSocketHelper.getSecondLastPartFromUrl(path);
                attributes.put(WebSocketHelper.userIdKey, userId);
                attributes.put(WebSocketHelper.roomId, roomID);
                return true;
            }



            @Override
            public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }

}
