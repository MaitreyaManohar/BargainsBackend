package com.example.oopsProject.UserClass;

public class addToCartClass {
    private long userid;
    private long productid;
    private int qtybought;

    public addToCartClass(long userid, long productid, int qtybought) {
        this.userid = userid;
        this.productid = productid;
        this.qtybought = qtybought;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public int getQtybought() {
        return qtybought;
    }

    public void setQtybought(int qtybought) {
        this.qtybought = qtybought;
    }
}
