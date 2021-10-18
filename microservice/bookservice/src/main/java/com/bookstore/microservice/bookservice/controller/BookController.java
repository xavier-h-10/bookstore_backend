package com.bookstore.microservice.bookservice.controller;


import com.bookstore.microservice.bookservice.dto.ResultDTO;
import com.bookstore.microservice.bookservice.entity.BookAuthorModel;
import com.bookstore.microservice.bookservice.service.BookService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BookController {

  BookService bookService;

  @Autowired
  void setBookService(BookService bookService) {
    this.bookService = bookService;
  }

  @RequestMapping("/findAuthorByBookName")
  public ResultDTO findAuthorByBookName(@RequestParam("bookName") String bookName) {
    log.info("bookName: {}", bookName);
    List<BookAuthorModel> res = bookService.findAuthorByBookName(bookName);
    return ResultDTO.success(res);
  }
}
