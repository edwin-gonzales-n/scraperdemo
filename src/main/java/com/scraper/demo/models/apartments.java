package com.scraper.demo.models;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "apartments")
public class apartments {

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

    @Column(nullable = false)
    private long property_id;


    public apartments(){}

    public apartments(long id, String title, String availability, String price, String date, String url, long property_id)
    {
        this.id = id;
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.date = date;
        this.url = url;
        this.property_id=property_id;
    }
//    Insert into database
    public apartments(String title, String availability, String price, String date, String url, long property_id)
    {
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.date = date;
        this.url = url;
        this.property_id = property_id;
    }

//    setters and getters
    public long getProperty_id() {
        return property_id;
    }

    public void setProperty_id(long property_id) {
        this.property_id = property_id;
    }

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
