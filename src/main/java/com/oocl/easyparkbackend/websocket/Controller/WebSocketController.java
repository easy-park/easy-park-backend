package com.oocl.easyparkbackend.websocket.Controller;


import com.oocl.easyparkbackend.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {


    @Autowired
    private WebSocket webSocket;

    @GetMapping("/sendMessage")
    public void sendOneWebSocket() {
        webSocket.sendAllMessage("ok");
    }
}
