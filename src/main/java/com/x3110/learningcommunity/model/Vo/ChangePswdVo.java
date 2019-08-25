package com.x3110.learningcommunity.model.Vo;

public class ChangePswdVo {
    private String username;

    private String oldPswd;

    private String newPswd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPswd() {
        return oldPswd;
    }

    public void setOldPswd(String oldPswd) {
        this.oldPswd = oldPswd;
    }

    public String getNewPswd() {
        return newPswd;
    }

    public void setNewPswd(String newPswd) {
        this.newPswd = newPswd;
    }
}
