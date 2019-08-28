package com.x3110.learningcommunity.service;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {
    private static int onlineCount = 0;//当前连接数
    private static CopyOnWriteArraySet<WebSocketServer> webSocketset
            = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketset.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

    }

    @OnClose
    public void onClose(){
        webSocketset.remove(this);
        subOnlineCount();
        System.out.println("有一处连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("来自客户端的消息" + message);

        for(WebSocketServer item : webSocketset){
            try{
                item.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount--;
    }
}
