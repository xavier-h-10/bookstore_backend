package com.bookstore.microservice.bookservice.repository;

import com.bookstore.microservice.bookservice.entity.Book;
import com.bookstore.microservice.bookservice.entity.BookAuthorModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {

  @Query(value = "select new com.bookstore.microservice.bookservice.entity.BookAuthorModel(f.bookId,f.name,f.author) from Book f where f.name like :bookName")
  List<BookAuthorModel> findAuthorByBookName(@Param("bookName") String bookName);
}
