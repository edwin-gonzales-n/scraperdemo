package com.scraper.demo;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "apartments")
public class HackerNewsItem {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Must have title")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Must have info availability")
    @Column(nullable = false)
    private String info;

    @NotBlank(message = "Must have price information")
    @Column(nullable = false)
    private String price;

    @NotBlank(message = "Must have current time")
    @Column(nullable = false)
    private String date;


    public HackerNewsItem(){}

    public HackerNewsItem(long id, String title, String info, String price, String date)
    {
        this.title = title;
        this.info = info;
        this.price = price;
        this.date = date;
    }
//    Insert into database
    public HackerNewsItem(String title, String info, String price, String date)
    {
        this.title = title;
        this.info = info;
        this.price = price;
        this.date = date;
    }

//    setters and getters

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

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
