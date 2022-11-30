package com.example.oopsProject.Mail;

public class ChangePwd {
    private long userId;
    private String oldPwd;
    private String newPwd;
    private String email;

    public ChangePwd(long userId,String oldPwd,String newPwd,String email) {
        this.userId = userId;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
