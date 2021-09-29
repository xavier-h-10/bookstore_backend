package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.entity.UserAuth;
import com.bookstore.service.BookService;
import com.bookstore.utils.messageUtils.Message;
import com.bookstore.utils.messageUtils.MessageUtil;
import com.bookstore.utils.sessionUtils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {

  BookService bookService;

  @Autowired
  void setBookService(BookService bookService) {
    this.bookService = bookService;
  }

  @RequestMapping("/getBookById")
  public Book getBookById(@RequestParam("id") Integer id) {
    return bookService.getBookByBookId(id);
  }

  /* 搜索框所用 通过书名模糊查找书籍 */
  @RequestMapping("/getBookByName")
  public List<Book> getBookByName(@RequestParam("name") String name) {
    return bookService.getBookByName(name);
  }

  //删除书籍 enabled=0
  @RequestMapping("/deleteBookById")
  public void deleteBookById(@RequestParam("id") Integer id) {
    bookService.deleteBookByBookId(id);
  }

  //添加书籍
  @RequestMapping("/addBook")
  public void addBook(@RequestBody Map<String, String> params) {
    bookService.addBook(params);
  }

  //更新书籍信息
  @RequestMapping("/updateBook")
  public void updateBook(@RequestBody Map<String, String> params) {
    bookService.updateBook(params);
  }

  @RequestMapping("/getBooks")
  public List<Book> getBooks() {
    return bookService.getBooks();
  }

  @RequestMapping("/getBooksByPage")
  public PageInfo<Book> getBooksByPage(@RequestParam("num") Integer num) {
    return bookService.getBooksByPage(num);

  }

}
