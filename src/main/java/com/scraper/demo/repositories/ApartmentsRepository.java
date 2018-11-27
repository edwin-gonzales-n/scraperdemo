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
    /*
     * custom query to pull data from DB by property_id
     */
    @Query(nativeQuery =true, value = "SELECT * from apartments WHERE property_id = ?1")
    List<apartments> findapartmentsByProperty_Id(long n);

    /*
     * custom query to truncate table once a day.  The app logic will then populate with new data.
     */
    @Query(nativeQuery = true, value = "truncate table apartments")
    @Modifying
    @Transactional
    void truncateApartmentsTable();

}