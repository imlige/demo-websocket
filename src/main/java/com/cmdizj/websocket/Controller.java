package com.cmdizj.websocket;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/controller")
public class Controller {

    //推送数据接口
    @PostMapping("/sendMessage2allClients")
    public void sendMessage2allClients(HttpServletRequest request) {
        WebSocketServer.sendMessage2allClients(request.getParameter("message"));
    }
}