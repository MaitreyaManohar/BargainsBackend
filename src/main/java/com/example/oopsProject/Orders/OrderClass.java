package com.example.oopsProject.Orders;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.UserClass.UserClass;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class OrderClass {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "order_sequence"
    )
    private long orderid;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private UserClass buyer;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemClass item;

    private LocalDate soldAt;



    private int qtyBought;
    OrderClass(){}

    public OrderClass(UserClass buyer, ItemClass item, LocalDate soldAt, int qtyBought) {
        this.buyer = buyer;
        this.item = item;
        this.soldAt = soldAt;

        this.qtyBought = qtyBought;
    }

    public UserClass getBuyer() {
        return buyer;
    }



    public ItemClass getItem() {
        return item;
    }



    public LocalDate getSoldAt() {
        return soldAt;
    }






    public int getQtyBought() {
        return qtyBought;
    }

    public void setQtyBought(int qtyBought) {
        this.qtyBought = qtyBought;
    }

    public long getOrderid() {
        return orderid;
    }


}
