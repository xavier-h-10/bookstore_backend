package com.bookstore.repository;

import com.bookstore.entity.BookDescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

//测试用 添加RepositoryRestResource注解
@Transactional
@RepositoryRestResource(collectionResourceRel = "book_description", path = "book_description")
public interface BookDescriptionRepository extends MongoRepository<BookDescription, Integer> {

}


