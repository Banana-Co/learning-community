package com.x3110.learningcommunity.model;

import java.time.LocalDateTime;
public class Notification {
    private String username;//进行操作的用户

    private int type;//1:点赞 2：评论

    private String message;

    private int read = 0;

    private LocalDateTime notifiDate;

    private int notifiNo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public LocalDateTime getNotifiDate() {
        return notifiDate;
    }

    public void setNotifiDate(LocalDateTime notifiDate) {
        this.notifiDate = notifiDate;
    }

    public int getNotifiNo() {
        return notifiNo;
    }

    public void setNotifiNo(int notifiNo) {
        this.notifiNo = notifiNo;
    }
}