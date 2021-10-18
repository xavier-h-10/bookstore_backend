package com.bookstore.microservice.bookservice.dao;

import com.bookstore.microservice.bookservice.entity.BookAuthorModel;
import java.util.List;

public interface BookDao {

  List<BookAuthorModel> findAuthorByBookName(String bookName);
}
