package com.bookstore.webservice_client.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.bookstore.webservice_client.webservice.BookSearchRequest;
import com.bookstore.webservice_client.webservice.BookSearchResponse;

public class BookSearchClient extends WebServiceGatewaySupport {

  private static final Logger log = LoggerFactory.getLogger(BookSearchClient.class);

  public BookSearchResponse getBooksByKeyword(String keyword) {

    BookSearchRequest request = new BookSearchRequest();
    request.setKeyword(keyword);

    log.info("Requesting keyword:" + keyword);

    BookSearchResponse response = (BookSearchResponse) getWebServiceTemplate()
        .marshalSendAndReceive("http://localhost:8080/ws/bookSearch", request);

    log.info("Get response length=" + response.getBooklist().size());
    return response;
  }
}

