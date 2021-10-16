package com.bookstore.webservice;

import com.bookstore.entity.BookInfo;
import com.bookstore.service.BookService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class BookSearchEndpoint {

  private static final String NAMESPACE_URI = "bookSearch";

  private BookService bookService;

  @Autowired
  public void BookSearchRequest(BookService bookService) {
    this.bookService = bookService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "bookSearchRequest")
  @ResponsePayload
  public BookSearchResponse getBooksByKeyword(@RequestPayload BookSearchRequest request) {
    BookSearchResponse response = new BookSearchResponse();
    String keyword = request.getKeyword();
    List<BookInfo> res = bookService.getBooksByKeyword(keyword);
    int len = res.size();
    for (int i = 0; i < len; i++) {
      BookInfo tmp = res.get(i);
      com.bookstore.webservice.BookInfo tmp1 = new com.bookstore.webservice.BookInfo();
      tmp1.setDescription(tmp.getDescription());
      tmp1.setName(tmp.getName());
      tmp1.setId(tmp.getId());
      response.getBooklist().add(tmp1);
    }
    return response;
  }
}
