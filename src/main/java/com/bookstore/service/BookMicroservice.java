package com.bookstore.service;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "bookservice", configuration = FeignAutoConfiguration.class)
public interface BookMicroservice {
  @RequestMapping("findAuthorByBookName")
  String findAuthorByBookName(@RequestParam("bookName") String bookName);
}
