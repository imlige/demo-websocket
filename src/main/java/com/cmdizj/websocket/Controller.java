package com.cmdizj.websocket;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controller")
public class Controller {

    //推送数据接口
    @PostMapping("/sendMessage2allClients")
    public void sendMessage2allClients(String message) {
        WebSocketServer.sendMessage2allClients( message );
    }
}