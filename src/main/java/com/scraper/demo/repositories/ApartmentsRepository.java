package com.scraper.demo.repositories;

import com.scraper.demo.models.apartments;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ApartmentsRepository extends CrudRepository<apartments, Long> {
//    @Query("select e from (select e from apartments order by id desc limit 12) sub order by id")
//    Iterable<apartments> findTop12ByOrderByIdDesc();  //works
//    Iterable<apartments> findAllByProperty_id(long property_id);

    @Query(nativeQuery =true, value = "SELECT * from apartments WHERE property_id = ?1")
    List<apartments> findapartmentsByProperty_Id(long n);

    @Query(nativeQuery = true, value = "truncate table apartments")
    @Modifying
    @Transactional
    void truncateApartmentsTable();

}