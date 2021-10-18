package com.bookstore.microservice.bookservice.service;

import com.bookstore.microservice.bookservice.entity.BookAuthorModel;
import java.util.List;

public interface BookService {

  List<BookAuthorModel> findAuthorByBookName(String bookName);
}
