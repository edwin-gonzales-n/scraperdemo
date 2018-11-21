package com.scraper.demo.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "amenities")
public class Amenities {
    
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)  // relationship to Apartments.id
    @JoinColumn (name = "apartment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Apartments apartment;

    @NotNull
    @Lob
    private String name;
    
    public Amenities(){}

    public Amenities(long id, Apartments apartment, String name) {
        this.id = id;
        this.apartment = apartment;
        this.name = name;
    }
    
    public Amenities(Apartments apartment, String name) {
        this.apartment = apartment;
        this.name = name;
    }

    public Amenities(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Apartments getApartment() {
        return apartment;
    }

    public void setApartment(Apartments apartment) {
        this.apartment = apartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
