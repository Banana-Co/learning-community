package com.x3110.learningcommunity.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.SessionScope;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketserver {
    private static int onlineCount = 0;//当前连接数
    private static CopyOnWriteArraySet<WebSocketserver> webSocketset
            = new CopyOnWriteArraySet<WebSocketserver>();

    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketset.add(this);
        addOnlineCount();
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        WebSocketserver.onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        WebSocketserver.onlineCount--;
    }
}
