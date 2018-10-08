package com.scraper.demo.repositories;

import com.scraper.demo.models.HackerNewsItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentsRepository extends CrudRepository<HackerNewsItem, Long> {
//    @Query("select e from (select e from apartments order by id desc limit 12) sub order by id")
//    Iterable<HackerNewsItem> findTop12ByOrderByIdDesc();  //works
    Iterable<HackerNewsItem> findTop12ByOrderByIdDesc();
    Iterable<HackerNewsItem> findTop9ByOrderByIdDesc();
    Iterable<HackerNewsItem> findTop16ByOrderByIdDesc();
    Iterable<HackerNewsItem> findTop13ByOrderByIdAsc();

}