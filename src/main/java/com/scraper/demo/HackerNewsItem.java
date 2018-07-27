package com.scraper.demo;

public class HackerNewsItem {
    private String title;
    private String info;
    public String price;



    public HackerNewsItem(String title, String info, String price)
    {
//        super();
        this.title = title;
        this.info = info;
        this.info = price;
    }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
