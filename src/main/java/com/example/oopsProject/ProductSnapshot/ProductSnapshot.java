package com.example.oopsProject.ProductSnapshot;


import com.example.oopsProject.Items.ItemClass;
import com.example.oopsProject.OrderSnapshot.OrderSnapshot;
import com.example.oopsProject.UserClass.Category;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ProductSnapshot")
public class ProductSnapshot {
    @Id
    @SequenceGenerator(
            name = "productsnapshot_sequence",
            sequenceName = "productsnapshot_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "productsnapshot_sequence"
    )
    long productsnapshotid;

    private long itemId;
    private String itemName;
    private boolean inProduction = true;
    private int qty;
    private Category category;

    private byte[] imageData;
    private int price;
    private int deliveryWithin;
    private int offer;

    private String description;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "item")
    private List<OrderSnapshot> orderSnapshots;
    private LocalDate offerValidTill;
    private LocalDate dateAdded;

    public ProductSnapshot(){}

    public ProductSnapshot(ItemClass item){
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.inProduction = item.isInProduction();
        this.qty = item.getQty();
        this.category = item.getCategory();
        this.imageData = null;;
        this.price = item.getPrice();
        this.deliveryWithin = item.getDeliveryWithin();
        this.offer = item.getOffer();
        this.offerValidTill = item.getOfferValidTill();
        this.dateAdded = item.getDateAdded();
        this.description = item.getDescription();
    }
    public ProductSnapshot(long productsnapshotid, long itemId, String itemName, boolean inProduction, int qty, Category category, byte[] imageData, int price, int deliveryWithin, int offer, String description, LocalDate offerValidTill, LocalDate dateAdded) {
        this.productsnapshotid = productsnapshotid;
        this.itemId = itemId;
        this.itemName = itemName;
        this.inProduction = inProduction;
        this.qty = qty;
        this.category = category;
        this.imageData =imageData;
        this.price = price;
        this.deliveryWithin = deliveryWithin;
        this.offer = offer;
        this.description = description;
        this.offerValidTill = offerValidTill;
        this.dateAdded = dateAdded;
    }

    public long getProductsnapshotid() {
        return productsnapshotid;
    }

    public void setProductsnapshotid(long productsnapshotid) {
        this.productsnapshotid = productsnapshotid;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
