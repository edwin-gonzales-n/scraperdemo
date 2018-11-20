package com.scraper.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "amenities")
public class amenity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne  // relationship to events.id
    @JoinColumn (name = "apartment_id")
    private apartments apartment_ID;

    @Column
    private String name;
    
    public amenity(){}

    public amenity(long id, apartments apartment_ID, String name) {
        this.id = id;
        this.apartment_ID = apartment_ID;
        this.name = name;
    }
    
    public amenity(apartments apartment_ID, String name) {
        this.apartment_ID = apartment_ID;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public apartments getApartment_ID() {
        return apartment_ID;
    }

    public void setApartment_ID(apartments apartment_ID) {
        this.apartment_ID = apartment_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
