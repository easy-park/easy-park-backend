package com.oocl.easyparkbackend.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/WebSocket")
public class WebSocket {

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();


    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    public void sendAllMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
