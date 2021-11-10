package com.bookstore.repository;

import com.bookstore.entity.BookImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookImageRepository extends MongoRepository<BookImage, Integer> {

}


