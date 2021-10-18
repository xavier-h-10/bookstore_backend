package com.bookstore.microservice.bookservice.daoimpl;

import com.bookstore.microservice.bookservice.dao.BookDao;
import com.bookstore.microservice.bookservice.entity.BookAuthorModel;
import com.bookstore.microservice.bookservice.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

  BookRepository bookRepository;

  @Autowired
  void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<BookAuthorModel> findAuthorByBookName(String bookName) {
    return bookRepository.findAuthorByBookName('%'+bookName+'%');
  }
}
