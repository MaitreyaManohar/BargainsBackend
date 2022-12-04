package com.example.oopsProject.UserClass;

import java.time.LocalDate;

public class    addItemClass {

    private String itemName;

    private int qty;

    private Category category;

    private long user_id;

    private int price;

    private int deliveryWithin;

    private int offer;

    private LocalDate offerValidTill;

    private String description;

    private LocalDate dateAdded;

    public addItemClass(Category category){
        this.category = category;

    }
    public addItemClass(String itemName, int qty, Category category, long user_id, int price, int deliveryWithin, int offer, LocalDate offerValidTill, LocalDate dateAdded,String description) {
        this.itemName = itemName;
        this.qty = qty;
        this.category = category;
        this.user_id = user_id;
        this.price = price;
        this.deliveryWithin = deliveryWithin;
        this.offer = offer;
        this.offerValidTill = offerValidTill;
        this.dateAdded = dateAdded;
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
