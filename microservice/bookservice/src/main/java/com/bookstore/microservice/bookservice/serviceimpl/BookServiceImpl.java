package com.bookstore.microservice.bookservice.serviceimpl;

import com.bookstore.microservice.bookservice.dao.BookDao;
import com.bookstore.microservice.bookservice.entity.BookAuthorModel;
import com.bookstore.microservice.bookservice.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  BookDao bookDao;

  @Autowired
  void setBookDao(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @Override
  public List<BookAuthorModel> findAuthorByBookName(String bookName) {
    return bookDao.findAuthorByBookName(bookName);
  }
}
