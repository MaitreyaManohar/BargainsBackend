package com.example.oopsProject.Admin;

public class ItemStatus {
    private String itemName;
    private int qtyLeft;

    public ItemStatus(String itemName,int qtyLeft) {
        this.itemName = itemName;
        this.qtyLeft = qtyLeft;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQtyLeft() {
        return qtyLeft;
    }

    public void setQtyLeft(int qtyLeft) {
        this.qtyLeft = qtyLeft;
    }
}
