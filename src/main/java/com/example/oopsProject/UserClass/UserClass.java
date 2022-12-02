package com.example.oopsProject.UserClass;

import com.example.oopsProject.Cart.Cart;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Orders.OrderClass;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table()
public class UserClass {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "user_sequence"
    )
    private long id;
    private String name;
    @Column(name = "Email",nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private long phoneNo;

    private boolean loggedin = false;
    private boolean approved = false;
    @Column(name="Address")
    String address;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderClass> orderClasses;

    @OneToMany(mappedBy = "userClass",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Cart> carts;

    private Role role;
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "ewallet_id")
    private Ewallet ewallet;


    private String password;

    public UserClass(String name, String email, long phoneNo, Role role,String password) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.role = role;
        this.password = password;
    }

    public UserClass(String name, String email, long phoneNo, String address, Role role, String password) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.role = role;
        this.password = password;
    }

    public UserClass(){

    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ewallet getEwallet() {
        return ewallet;
    }

    public void setEwallet(Ewallet ewallet) {
        this.ewallet = ewallet;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
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
}
