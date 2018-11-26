/* Scraper app for the AR/VR Virtual Pillar team
 * Apartment Rest Controller class
 * This RestController class will parce the information within the database and trasnform it to
 * json objects and serve it whenever the mapping attributes are called.
 */
package com.scraper.demo.controllers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.scraper.demo.models.apartments;
import com.scraper.demo.repositories.ApartmentsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Edwin O. Gonzales
 */

@RestController
public class ApartmentsController {

    private final ApartmentsRepository apartmentsRepository;

    public ApartmentsController(ApartmentsRepository apartmentsRepository) {
        this.apartmentsRepository = apartmentsRepository;
    }

    /*
     * All the objects use pre-set property IDs variables in order to filter the database by property_ID
     * i.e - select * from apartments where property_id=?
     * You can take a look at the database to have a better idea.
     * Please see ApartmentsRepository for db query function.
     */
    @GetMapping("/nuecesapartments")
    public Iterable<apartments> showNuecesApartment() {
        long property_id=1;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/lakeshore-pearl")
    public Iterable<apartments> showPearlShore() {
        long property_id=2;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/azulapartments")
    public Iterable<apartments> showAzulApartments() {
        long property_id=3;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/lenox-boardwalk")
    public Iterable<apartments> showLenoxApartments() {
        long property_id=4;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/7east-austin")
    public Iterable<apartments> show7EastApartments() {
        long property_id=5;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

}