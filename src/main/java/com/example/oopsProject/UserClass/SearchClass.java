package com.example.oopsProject.UserClass;

public class SearchClass {
    String search;
    long userid;

    public SearchClass(String search, long userid) {
        this.search = search;
        this.userid = userid;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
