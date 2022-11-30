package com.example.oopsProject.Admin;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;

public class getOrdersClass {
    private long id;
    private long requesterId;
    private LocalDate date;

    public getOrdersClass() {
    }

    public getOrdersClass(long id, long requesterId, LocalDate date) {
        this.id = id;
        this.date = date;
        this.requesterId = requesterId;
    }
    public getOrdersClass(long requesterId,LocalDate date){
        this.requesterId = requesterId;
        this.date = date;
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
