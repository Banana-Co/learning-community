package com.x3110.learningcommunity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "report")
public class Report {
    @Id
    private String Id;

    private String reportUsername;//举报用户

    private String usernameReported;//被举报用户

    private String fatherId;//帖子Id

    private int no;//楼层

    private String reason;

    private LocalDateTime createdDate;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getReportUsername() {
        return reportUsername;
    }

    public void setReportUsername(String reportUsername) {
        this.reportUsername = reportUsername;
    }

    public String getUsernameReported() {
        return usernameReported;
    }

    public void setUsernameReported(String usernameReported) {
        this.usernameReported = usernameReported;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
