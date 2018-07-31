package com.scraper.demo.repositories;

import com.scraper.demo.models.HackerNewsItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentsRepository extends CrudRepository<HackerNewsItem, Long> {}