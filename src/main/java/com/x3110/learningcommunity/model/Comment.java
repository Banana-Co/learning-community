package com.x3110.learningcommunity.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "comment")
public class Comment {

    private String content;

    private String author;

    private String fatherId;

    private String avatarUrl;

    private int no = 0;
    private int fatherNo = -1;
    private int valid = 1;

    private int likeNum = 0;

    @CreatedDate
    private LocalDateTime createdDate;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }


    public String getFatherId() {
        return fatherId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getFatherNo() {
        return fatherNo;
    }

    public void setFatherNo(int fatherNo) {
        this.fatherNo = fatherNo;
    }
}
