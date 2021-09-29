package com.bookstore.dao;

import com.bookstore.entity.Book;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

public interface BookDao {

  Book getBookByBookId(Integer bookId);

  List<Book> getBooks();

  void deleteBookByBookId(Integer bookId);

  void addBook(Map<String, String> params);

  List<Book> getBookByName(String name);

  void updateBook(Map<String,String> params);

  PageInfo<Book> getBooksByPage(Integer num);
}
