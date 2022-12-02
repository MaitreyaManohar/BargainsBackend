package com.example.oopsProject.OutputClasses;

import com.example.oopsProject.Orders.OrderClass;

import java.time.LocalDate;

public class OrderOutput {
    private long orderid;


    private UserOutput buyer;


    private ProductOutput item;

    private LocalDate soldAt;




    private int qtyBought;

    public OrderOutput(long orderid, UserOutput buyer, ProductOutput item, LocalDate soldAt, int qtyBought) {
        this.orderid = orderid;
        this.buyer = buyer;
        this.item = item;
        this.soldAt = soldAt;

        this.qtyBought = qtyBought;
    }

    public OrderOutput(OrderClass order){
        this.orderid = order.getOrderid();
        this.buyer = new UserOutput(order.getBuyer());
        this.item = new ProductOutput(order.getItem());
        this.soldAt = order.getSoldAt();
        this.qtyBought = order.getQtyBought();

    }

    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public UserOutput getBuyer() {
        return buyer;
    }

    public void setBuyer(UserOutput buyer) {
        this.buyer = buyer;
    }

    public ProductOutput getItem() {
        return item;
    }

    public void setItem(ProductOutput item) {
        this.item = item;
    }

    public LocalDate getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDate soldAt) {
        this.soldAt = soldAt;
    }


    public int getQtyBought() {
        return qtyBought;
    }

    public void setQtyBought(int qtyBought) {
        this.qtyBought = qtyBought;
    }
}
