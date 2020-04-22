package com.cmdizj.websocket;
import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/WebSocketServer")
public class WebSocketServer {

    /**
     * 与某一个客户端的连接会话
     * 用这个 session 向客户端发消息
     */
    private Session session;

    // 向所有客户端【浏览器】发消息
    public static void sendMessage2allClients(String message){
        for (WebSocketServer item : DB.webSocketServers) {
            try {
                // 发消息
                item.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                // 给某一个客户端【浏览器】发消息抛异常，没关系，继续给下一个客户端【浏览器】发消息
                continue;
            }
        }
    }


    // 当前端创建连接【 new WebSocket("ws://localhost:8080/websocket") 】时，调用的方法
    @OnOpen
    public void clientConnected(Session session) {
        DB.increaseConnectedClientNum();
        DB.webSocketServers.add(this);
        this.session = session;
        sendMessage2allClients("系统消息：SESSION_ID 为 " + this.session.getId() + " 的客户端连接成功");
    }


    // 收到客户端【浏览器】消息后调用的方法
    @OnMessage
    public void receiveMessageFromClient(String message) {
        sendMessage2allClients("SESSION_ID " + this.session.getId() + " : " + message);
    }


    //
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }


    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        DB.decreaseConnectedClientNum();
        DB.webSocketServers.remove(this);
        sendMessage2allClients("系统消息：SESSION_ID 为 " + this.session.getId() + " 的客户端断开了连接");
    }

}
