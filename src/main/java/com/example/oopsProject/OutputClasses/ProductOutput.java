package com.example.oopsProject.OutputClasses;

import com.example.oopsProject.Images.ImageData;
import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.ProductSnapshot.ProductSnapshot;
import com.example.oopsProject.UserClass.Category;

import java.time.LocalDate;

public class ProductOutput {
    private long itemId;
    private String itemName;
    private boolean inProduction = true;
    private int qty;
    private Category category;

    private ImageData image;
    private int price;
    private int deliveryWithin;
    private int offer;

    private String description;
    private LocalDate offerValidTill;
    private LocalDate dateAdded;

    public ProductOutput(long itemId, String itemName, boolean inProduction, int qty, Category category, ImageData image, int price, int deliveryWithin, int offer, LocalDate offerValidTill, LocalDate dateAdded,String description) {
        this.itemId = itemId;
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
        this.description = description;
    }

    public ProductOutput(ItemClass item){
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.inProduction = item.isInProduction();
        this.qty = item.getQty();
        this.category = item.getCategory();
        this.image = item.getImage();
        this.price = item.getPrice();
        this.deliveryWithin = item.getDeliveryWithin();
        this.offer = item.getOffer();
        this.offerValidTill = item.getOfferValidTill();
        this.dateAdded = item.getDateAdded();
        this.description = item.getDescription();
    }

    public ProductOutput(ProductSnapshot productSnapshot){
        this.itemId = productSnapshot.getItemId();
        this.itemName = productSnapshot.getItemName();
        this.inProduction = productSnapshot.isInProduction();
        this.qty = productSnapshot.getQty();
        this.category = productSnapshot.getCategory();
        this.image = null;
        this.price = productSnapshot.getPrice();
        this.deliveryWithin = productSnapshot.getDeliveryWithin();
        this.offer = productSnapshot.getOffer();
        this.offerValidTill = productSnapshot.getOfferValidTill();
        this.dateAdded = productSnapshot.getDateAdded();
        this.description = productSnapshot.getDescription();
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
