package com.scraper.demo.repositories;

import com.scraper.demo.models.apartments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentsRepository extends CrudRepository<apartments, Long> {
//    @Query("select e from (select e from apartments order by id desc limit 12) sub order by id")
//    Iterable<apartments> findTop12ByOrderByIdDesc();  //works
    Iterable<apartments> findTop12ByOrderByIdDesc();
    Iterable<apartments> findTop9ByOrderByIdDesc();
    Iterable<apartments> findTop16ByOrderByIdDesc();
    Iterable<apartments> findTop13ByOrderByIdAsc();

}