package com.bookstore.repository;

import com.bookstore.entity.HomeItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import javax.transaction.Transactional;

@Transactional
public interface HomeRepository extends MongoRepository<HomeItem, Integer> {

}

