package com.bookstore.webservice_client.controller;


import com.bookstore.webservice_client.webservice.BookSearchClient;
import com.bookstore.webservice_client.webservice.BookSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  @Autowired
  BookSearchClient bookSearchClient;

  @RequestMapping("/searchBook")
  public BookSearchResponse getBooksByKeyword(@RequestParam("keyword") String keyword) {
    return bookSearchClient.getBooksByKeyword(keyword);
  }
}
