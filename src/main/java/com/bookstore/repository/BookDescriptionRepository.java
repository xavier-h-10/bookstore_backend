package com.bookstore.repository;

import com.bookstore.entity.BookDescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookDescriptionRepository extends MongoRepository<BookDescription, Integer> {

}


