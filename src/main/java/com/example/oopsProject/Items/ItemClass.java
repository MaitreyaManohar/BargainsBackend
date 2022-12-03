package com.example.oopsProject.Items;

import com.example.oopsProject.Images.ImageData;
import com.example.oopsProject.Orders.OrderClass;
import com.example.oopsProject.UserClass.Category;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Items")
public class ItemClass {
    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "item_sequence"
    )
    private long itemId;
    private String itemName;
    private boolean inProduction = true;
    private int qty;
    private Category category;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "item")
    private List<OrderClass> orderClasses;


    @Lob
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private ImageData image;
    private int price;
    private int deliveryWithin;
    private int offer=0;
    private LocalDate offerValidTill;

    private LocalDate dateAdded;

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public ItemClass(){

    }

    public ItemClass(String itemName, boolean inProduction, int qty, Category category, int price, int deliveryWithin, int offer, LocalDate offerValidTill, LocalDate dateAdded) {
        this.itemName = itemName;
        this.inProduction = inProduction;
        this.qty = qty;
        this.category = category;
        this.price = price;
        this.deliveryWithin = deliveryWithin;
        this.offer = offer;
        this.offerValidTill = offerValidTill;
        this.dateAdded = dateAdded;
    }

    public ItemClass(String itemName, int qty, Category category, ImageData image, int price, int deliveryWithin) {
        this.itemName = itemName;
        this.qty = qty;
        this.category = category;
        this.image = image;
        this.price = price;
        this.deliveryWithin = deliveryWithin;
    }

    public ItemClass(String itemName, int qty, Category category, int price, int deliveryWithin, int offer, LocalDate offerValidTill, LocalDate dateAdded) {
        this.itemName = itemName;
        this.qty = qty;
        this.category = category;
        this.price = price;
        this.deliveryWithin = deliveryWithin;
        this.offer = offer;
        this.offerValidTill = offerValidTill;
        this.dateAdded = dateAdded;
    }

    public ItemClass(String itemName, boolean inProduction, int qty, Category category, ImageData image, int price, int deliveryWithin, int offer, LocalDate offerValidTill, LocalDate dateAdded) {
        this.itemName = itemName;
        this.inProduction = inProduction;
        this.qty = qty;
        this.category = category;
        this.image = image;
        this.price = price;
        this.deliveryWithin = deliveryWithin;
        this.offer = offer;
        this.offerValidTill = offerValidTill;
        this.dateAdded = dateAdded;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ImageData getImage() {
        return image;
    }

    public void setImage(ImageData image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDeliveryWithin() {
        return deliveryWithin;
    }

    public void setDeliveryWithin(int deliveryWithin) {
        this.deliveryWithin = deliveryWithin;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public LocalDate getOfferValidTill() {
        return offerValidTill;
    }

    public void setOfferValidTill(LocalDate offerValidTill) {
        this.offerValidTill = offerValidTill;
    }

    public int priceWithOffer(){
        return ((100-this.offer)*this.price)/100;
    }
}
