package com.scraper.demo.repositories;

import com.scraper.demo.models.Apartments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.scraper.demo.models.Amenities;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AmenitiesRepository extends CrudRepository<Amenities, Long>{

    @Query(nativeQuery = true, value = "truncate table amenities")
    @Modifying
    @Transactional
    void truncateAmenitiesTable();

    @Query(nativeQuery =true, value = "SELECT * from amenities WHERE apartment_id = ?1")
    List<Apartments> findapartmentsByApartment_Id(long n);
}
