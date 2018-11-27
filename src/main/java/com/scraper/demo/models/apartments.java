/* Scraper app for the AR/VR Virtual Pillar team
 * Apartment model.
 * This class is used for building the database tables.  Also, the constructors to either insert or pull data from the DB.
 */

package com.scraper.demo.models;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "apartments")
public class apartments {

    @Id
    @GeneratedValue
    private long id; //auto generated

    @NotBlank(message = "Must have title")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Must have availability availability")
    @Column(nullable = false)
    private String availability;

    @NotBlank(message = "Must have price availabilityrmation")
    @Column(nullable = false)
    private String price;

    @NotBlank(message = "Must have current time updated")
    @Column(nullable = false)
    private String updated;

    @NotBlank(message = "Must have current url")
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private long property_id;

    @Column
    private String location;


    public apartments(){}//open constructor

    public apartments(long id, String title, String availability, String price, String updated, String url, long property_id, String location)
    {
        this.id = id;
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.updated = updated;
        this.url = url;
        this.property_id = property_id;
        this.location = location;
    } // pull from DB

    public apartments(String title, String availability, String price, String updated, String url, long property_id, String location)
    {
        this.title = title;
        this.availability = availability;
        this.price = price;
        this.updated = updated;
        this.url = url;
        this.property_id = property_id;
        this.location = location;
    } // insert into DB.

//    setters and getters

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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
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
