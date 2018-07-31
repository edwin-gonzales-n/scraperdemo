package com.scraper.demo.repositories;

import com.scraper.demo.models.HackerNewsItem;
import org.springframework.data.repository.CrudRepository;

public interface ApartmentsRepository extends CrudRepository<HackerNewsItem, Long> {

}