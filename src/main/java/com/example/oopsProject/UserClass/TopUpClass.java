package com.example.oopsProject.UserClass;

public class TopUpClass
{

    private long user_id;
    private long balance;

    private String password;

    public TopUpClass(long user_id, long balance, String password) {
        this.user_id = user_id;
        this.balance = balance;
        this.password = password;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
