package com.bookstore.serviceimpl;

import com.bookstore.dao.BookDao;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookInfo;
import com.bookstore.search.SolrSearching;
import com.bookstore.service.BookService;
import com.github.pagehelper.PageInfo;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

  BookDao bookDao;

  @Autowired
  void setBookDao(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @Override
  public List<Book> getBooks() {
    return bookDao.getBooks();
  }

  @Override
  public Book getBookByBookId(Integer bookId) {
    return bookDao.getBookByBookId(bookId);
  }

  @Override
  public void deleteBookByBookId(Integer bookId) {
    bookDao.deleteBookByBookId(bookId);
  }

  @Override
  public void addBook(Map<String, String> params) {
    bookDao.addBook(params);
  }

  @Override
  public List<Book> getBookByName(String name) {
    return bookDao.getBookByName(name);
  }

  @Override
  public void updateBook(Map<String, String> params) {
    bookDao.updateBook(params);
  }

  @Override
  public PageInfo<Book> getBooksByPage(Integer num) {
    return bookDao.getBooksByPage(num);
  }

  @Override
  public List<BookInfo> getBooksByKeyword(String keyword) {
    return SolrSearching.getBooksByKeyword(keyword);
  }
}
