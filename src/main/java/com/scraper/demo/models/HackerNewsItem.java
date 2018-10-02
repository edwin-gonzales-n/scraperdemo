package com.scraper.demo.models;

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

    @NotBlank(message = "Must have availability availability")
    @Column(nullable = false)
    private String availability;

    @NotBlank(message = "Must have price availabilityrmation")
    @Column(nullable = false)
    private String price;

    @NotBlank(message = "Must have current time")
    @Column(nullable = false)
    private String date;

    @NotBlank(message = "Must have current url")
    @Column(nullable = false)
    private String url;


    public HackerNewsItem(){}

    public HackerNewsItem(long id, String title, String availability, String price, String date, String url)
    {
        this.id = id;
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.date = date;
        this.url = url;
    }
//    Insert into database
    public HackerNewsItem(String title, String availability, String price, String date, String url)
    {
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.date = date;
        this.url = url;
    }

//    setters and getters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
