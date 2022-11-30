package com.example.oopsProject.OutputClasses;

import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.UserClass.Role;
import com.example.oopsProject.UserClass.UserClass;

import javax.persistence.Column;

public class UserOutput {
    private String name;
    private String email;
    private long phoneNo;

    private String address;

    private Ewallet ewallet;
    private Role role;

    public UserOutput(String name, String email, long phoneNo, Role role) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.role = role;
    }

    public UserOutput(String name, String email, long phoneNo, Ewallet ewallet, Role role,String address) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.ewallet = ewallet;
        this.role = role;
        this.address = address;
    }

    public UserOutput(UserClass userClass){
        this.name = userClass.getName();
        this.email = userClass.getEmail();
        this.phoneNo = userClass.getPhoneNo();
        this.ewallet = userClass.getEwallet();
        this.role = userClass.getRole();
        this.address = userClass.getAddress();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Ewallet getEwallet() {
        return ewallet;
    }

    public void setEwallet(Ewallet ewallet) {
        this.ewallet = ewallet;
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
}
