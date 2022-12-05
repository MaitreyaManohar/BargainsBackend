package com.example.oopsProject.OrderSnapshot;

import com.example.oopsProject.Cart.Cart;
import com.example.oopsProject.ProductSnapshot.ProductSnapshot;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class OrderSnapshot {
    @Id
    @SequenceGenerator(
            name = "ordersnapshot_sequence",
            sequenceName = "ordersnapshot_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "ordersnapshot_sequence"
    )
    long orderSnapshotId;


    private int qtybought;

    @ManyToOne
    @JoinColumn(name = "productsnapshotid")
    private ProductSnapshot item;

    private LocalDate soldAt;


    private long buyerid;

    public OrderSnapshot(){}
    public OrderSnapshot(long orderSnapshotId,  int qtybought, ProductSnapshot productSnapshot, LocalDate soldAt, long buyerid) {
        this.orderSnapshotId = orderSnapshotId;
        this.qtybought = qtybought;
        this.item = productSnapshot;
        this.soldAt = soldAt;
        this.buyerid = buyerid;
    }

    public OrderSnapshot(int qtybought, ProductSnapshot item, LocalDate soldAt, long buyerid) {
        this.qtybought = qtybought;
        this.item = item;
        this.soldAt = soldAt;
        this.buyerid = buyerid;
    }



    public OrderSnapshot(Cart cart){
        this.qtybought = cart.getQtybought();
        this.soldAt = LocalDate.now();
        this.buyerid = cart.getUserClass().getId();
    }
    public long getOrderSnapshotId() {
        return orderSnapshotId;
    }

    public void setOrderSnapshotId(long orderSnapshotId) {
        this.orderSnapshotId = orderSnapshotId;
    }



    public int getQtybought() {
        return qtybought;
    }

    public void setQtybought(int qtybought) {
        this.qtybought = qtybought;
    }

    public ProductSnapshot getItem() {
        return item;
    }

    public void setItem(ProductSnapshot item) {
        this.item = item;
    }

    public LocalDate getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDate soldAt) {
        this.soldAt = soldAt;
    }

    public long getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(long buyerid) {
        this.buyerid = buyerid;
    }
}
