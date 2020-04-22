package com.cmdizj.websocket;

import java.util.concurrent.CopyOnWriteArraySet;

public class DB {
    /**
     * 记录 有多少个客户端【前端浏览器】与服务端【后端】创建连接 的个数
     * 应该把它设计成线程安全的 , 使用 synchronized 进行操作
     */
    private static Integer connectedClientNum = 0;
    public static synchronized void increaseConnectedClientNum() {
        connectedClientNum++;
    }
    public static synchronized void decreaseConnectedClientNum() {
        connectedClientNum--;
    }

    /**
     * java.util.concurrent 包的线程安全的 Set 集合
     * 客户端【前端浏览器】 每次 使用 new WebSocket("ws://localhost:8080/websocket") 时，即与服务端【后端】创建连接时，后端 new 一个 WebSocketServer
     *      【 可见 WebSocketServer 是多例的 --> 前端有多少个 new WebSocket("ws://localhost:8080/websocket")   就后端有多少个 new WebSocketServer 】
     * WebSocketServers 用来存储 总共有多少个 WebSocketServer 被 new 了
     */
    public static CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();
}
