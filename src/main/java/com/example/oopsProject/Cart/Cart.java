package com.example.oopsProject.Cart;

import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.UserClass.UserClass;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Table
public class Cart {
    @Id
    @SequenceGenerator(
            name = "cartId_sequence",
            sequenceName = "cartId_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "cartId_sequence"
    )
    long cartid;
    int qtybought;
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserClass userClass;
    @ManyToOne
    @JoinColumn(name = "item_id")
    ItemClass itemClass;

    public Cart(){}
    public Cart(int qtybought, UserClass userClass, ItemClass itemClass) {
        this.qtybought = qtybought;
        this.userClass = userClass;
        this.itemClass = itemClass;
    }

    public Cart(long cartid, int qtybought, UserClass userClass, ItemClass itemClass) {
        this.cartid = cartid;
        this.qtybought = qtybought;
        this.userClass = userClass;
        this.itemClass = itemClass;
    }

    public long getCartid() {
        return cartid;
    }

    public int getQtybought() {
        return qtybought;
    }

    public void setQtybought(int qtybought) {
        this.qtybought = qtybought;
    }

    public UserClass getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClass userClass) {
        this.userClass = userClass;
    }

    public ItemClass getItemClass() {
        return itemClass;
    }

    public void setItemClass(ItemClass itemClass) {
        this.itemClass = itemClass;
    }
}
