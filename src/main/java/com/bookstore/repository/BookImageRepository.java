package com.bookstore.repository;

import com.bookstore.entity.BookImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Transactional
@RepositoryRestResource(collectionResourceRel = "book_image", path = "book_image")
public interface BookImageRepository extends MongoRepository<BookImage, Integer> {

}


