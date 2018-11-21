package com.scraper.demo.repositories;

import com.scraper.demo.models.Apartments;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApartmentsRepository extends CrudRepository<Apartments, Long> {
//    @Query("select e from (select e from Apartments order by id desc limit 12) sub order by id")
//    Iterable<Apartments> findTop12ByOrderByIdDesc();  //works
//    Iterable<Apartments> findAllByProperty_id(long property_id);

    @Query(nativeQuery =true, value = "SELECT id, availability, floorplan, location, price, property_id, title, updated, url from apartments WHERE property_id = ?1")
    List<Apartments> findapartmentsByProperty_Id(long n);

    @Query(nativeQuery = true, value = "truncate table apartments")
    @Modifying
    @Transactional
    void truncateApartmentsTable();

}