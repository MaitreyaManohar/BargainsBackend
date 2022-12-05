package com.example.oopsProject.Admin;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;

public class getOrdersClass {
    private long id;
    private long requesterId;
    private LocalDate date;
    private int year;
    private int month;
    private String email;
    public getOrdersClass() {
    }

    public getOrdersClass(long id, long requesterId, LocalDate date,String email,int month,int year) {
        this.id = id;
        this.email = email;
        this.date = date;
        this.requesterId = requesterId;
        this.month = month;
        this.year = year;
    }
    public getOrdersClass(long requesterId,LocalDate date){
        this.requesterId = requesterId;
        this.date = date;
    }

    public getOrdersClass(long requesterId, int month){
        this.requesterId = requesterId;
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
