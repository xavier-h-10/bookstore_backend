package com.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookInfo;
import com.bookstore.entity.BookNode;
import com.bookstore.entity.BookTag;
import com.bookstore.entity.UserAuth;
import com.bookstore.repository.BookNodeRepository;
import com.bookstore.search.SolrIndexing;
import com.bookstore.service.BookMicroservice;
import com.bookstore.service.BookService;
import com.bookstore.utils.messageUtils.Message;
import com.bookstore.utils.messageUtils.MessageUtil;
import com.bookstore.utils.sessionUtils.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.alibaba.fastjson.JSONArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class BookController {

  BookService bookService;

  BookMicroservice bookMicroservice;

  @Autowired
  void setBookService(BookService bookService) {
    this.bookService = bookService;
  }

  @Autowired
  void setBookMicroservice(BookMicroservice bookMicroservice) {
    this.bookMicroservice = bookMicroservice;
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

  //全文搜索 导出简介信息
  @RequestMapping("/exportBookInfo")
  public JSONArray exportBookInfo() {
    List<Book> res = bookService.getBooks();
    int len = res.size();
    JSONArray jsonArray = new JSONArray();
    List<Map<String, String>> list = new ArrayList<>();
    for (int i = 0; i < len; i++) {
      Integer bookId = res.get(i).getBookId();
      String name = res.get(i).getName();
      String description = res.get(i).getDescription();
      log.info(bookId + "," + name + "," + description);
      Map<String, String> map = new HashMap<>();
      map.clear();
      map.put("id", String.valueOf(bookId));
      map.put("name", String.valueOf(name));
      map.put("description", description);
      list.add(map);
    }
    jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
    return jsonArray;
  }

  //全文搜索api
  @RequestMapping("/getBooksByKeyword")
  public List<BookInfo> getBooksByKeyword(@RequestParam("keyword") String keyword) {
    return bookService.getBooksByKeyword(keyword);
  }

  //微服务互相调用
  @RequestMapping("/findAuthorByBookName")
  public String findAuthorByBookName(@RequestParam("bookName") String bookName) {
    log.info("bookController: findAuthorByBookName called bookName={}", bookName);
    return bookMicroservice.findAuthorByBookName(bookName);
  }

  @Autowired
  BookNodeRepository bookNodeRepository;

  @RequestMapping("/findBookNodeByName")
  public void findBookNodeByName(@RequestParam("bookName")String bookName) {
    log.info("bookController: findAuthorByBookName called bookName={}", bookName);
    BookNode node=bookNodeRepository.findBookNodeByName(bookName);
    if(node!=null) {
      log.info("find node!");
      for(BookTag p: node.neighbors) {
        log.info("tag="+p.getName());
      }
    }
  }

  @RequestMapping("/getBookTags")
  public List<BookTag> getBookTags() {
    return bookService.getBookTags();
  }

  @RequestMapping("/findRelatedBooksByTag")
  public JSONObject findRelatedBooksByTags(@RequestParam("tagId") String tagId) {
    return bookService.findRelatedBooksByTags(tagId);
  }
}
