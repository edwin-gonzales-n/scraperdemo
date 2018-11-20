package com.scraper.demo.models;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apartments")
public class apartments {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Must have title")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Must have price")
    @Column(nullable = false)
    private String price;

    @NotBlank(message = "Must have availability availability")
    @Column
    private String availability;

    @NotBlank(message = "Must have date when last scraped")
    @Column(nullable = false)
    private String updated;

    @NotBlank(message = "Must have current url")
    @Column(nullable = false)
    private String url;

    @Column
    private String floorplan;

    @NotBlank(message = "Must have location")
    @Column(nullable = false)
    private String location;

    @NotBlank(message = "Must have property_id")
    @Column(nullable = false)
    private long property_id;

    // tickets added by Edwin
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment_id")
    private List<amenity> amenities;

    public apartments(){}

    public apartments(long id, String title, String availability, String price, String updated, String url, List<amenity> amenities, String floorplan, String location, long property_id)
    {
        this.id = id;
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.updated = updated;
        this.url = url;
        this.amenities = amenities;
        this.floorplan = floorplan;
        this.location = location;
        this.property_id=property_id;
    }
//    Insert into database
    public apartments(String title, String availability, String price, String updated, String url, List<amenity> amenities, String floorplan, String location,long property_id)
    {
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.updated = updated;
        this.url = url;
        this.amenities = amenities;
        this.floorplan = floorplan;
        this.location = location;
        this.property_id = property_id;
    }

//    setters and getters


    public List<amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<amenity> amenities) {
        this.amenities = amenities;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getFloorplan() {
        return floorplan;
    }

    public void setFloorplan(String floorplan) {
        this.floorplan = floorplan;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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
