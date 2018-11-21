package com.scraper.demo.controllers;

import com.scraper.demo.models.Apartments;
import com.scraper.demo.repositories.ApartmentsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApartmentsController {

    private final ApartmentsRepository apartmentsRepository;

    public ApartmentsController(ApartmentsRepository apartmentsRepository) {
        this.apartmentsRepository = apartmentsRepository;
    }

    @GetMapping("/nuecesapartments")
    public Iterable<Apartments> showNuecesApartment() {
        long property_id=1;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/lakeshore-pearl")
    public Iterable<Apartments> showPearlShore() {
        long property_id=2;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/azulapartments")
    public Iterable<Apartments> showAzulApartments() {
        long property_id=3;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

    @GetMapping("/lenox-boardwalk")
    public Iterable<Apartments> showLenoxApartments() {
        long property_id=4;
        return apartmentsRepository.findapartmentsByProperty_Id(property_id);
    }

}