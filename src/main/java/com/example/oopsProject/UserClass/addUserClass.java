package com.example.oopsProject.UserClass;

import javax.persistence.Column;

public class addUserClass {
    private long id;
    private String name;
    private String email;
    private long phoneNo;
    private Role role;
    private long balance;

    private String address;
    private String password;

    public addUserClass(String name, String email, long phoneNo, Role role, long balance,String password,String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
        this.role = role;
        this.balance = balance;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }


}
