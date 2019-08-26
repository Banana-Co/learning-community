package com.x3110.learningcommunity.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document(collection = "post")
public class Post {
    @Id
    private String id;

    private Long postId;

    private String title;

    private String content;

    private String author;

    private int valid=1;//帖子的有效性，1代表有效，0表示被删除，但依然存在数据库中，只是在页面中不显示了

    private int permission; //权限等级

    private ArrayList<Comment> commentArrayList;

    @CreatedDate
    private LocalDateTime createdDate;

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdDate = createdTime;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }
}
