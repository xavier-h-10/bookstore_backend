package com.bookstore.daoimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.bookstore.dao.BookDao;
import com.bookstore.entity.Book;
import com.bookstore.entity.HomeItem;
import com.bookstore.repository.BookRepository;
import com.bookstore.utils.redisUtils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class BookDaoImpl implements BookDao {

  BookRepository bookRepository;
  RedisUtil redisUtil;

  @Autowired
  void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Autowired
  void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  @Override
  public List<Book> getBooks() {
    String key = "bookList";
    Object p = redisUtil.get(key);
    List<Book> res = null;
    if (p == null) {
      log.info("Get bookList from database.");
      res = bookRepository.getBooks();
      redisUtil.set(key, JSONArray.toJSON(res));
      redisUtil.expire(key, 600);
    } else {
      log.info("Get bookList from Redis.");
      res = JSONArray.parseObject(p.toString(), new TypeReference<List<Book>>() {
      });
    }
    return res;
  }

  @Override
  public Book getBookByBookId(Integer bookId) {
    String key = "book-" + bookId;
    Object p = redisUtil.get(key);
    Book res = null;
    if (p == null) {
      log.info("Get " + key + " from database.");
      res = bookRepository.getBookByBookId(bookId);
      redisUtil.set(key, JSONArray.toJSON(res));
      //书籍的详情信息可能更新较频繁，因此过期时间设置较小
      redisUtil.expire(key, 60);
    } else {
      log.info("Get " + key + " from Redis.");
      res = JSONArray.parseObject(p.toString(), new TypeReference<Book>() {
      });
    }
    return res;
  }

  @Override
  public void deleteBookByBookId(Integer bookId) {
    bookRepository.deleteBookByBookId(bookId);
    deleteBookCache(bookId);
  }

  @Override
  public void addBook(Map<String, String> params) {
    String name = params.get("name");
    String author = params.get("author");
    String isbn = params.get("isbn");
    String description = params.get("description");
    String image = params.get("image");
    String type = params.get("type");
    String brief = params.get("brief");
    if (name == null || author == null || isbn == null || description == null || image == null
        || type == null || brief == null) {
      return;
    }

    if (params.get("price") == null) {
      return;
    }
    BigDecimal price = new BigDecimal(params.get("price"));
    price = price.setScale(2, BigDecimal.ROUND_HALF_UP);  //保留两位，四舍五入

    if (params.get("inventory") == null) {
      return;
    }
    Integer inventory = new Integer(params.get("inventory"));

    bookRepository.addBook(name, author, price, isbn, inventory, description, image, type, brief);
    deleteBookCache(-1);
  }

  @Override
  public List<Book> getBookByName(String name) {
    return bookRepository.getBookByName("%" + name + "%");
  }

  @Override
  public void updateBook(Map<String, String> params) {
    Integer bookId = Integer.valueOf(params.get("bookId"));
    if (bookId == null || bookId <= 0) {
      return;
    }

    String name = params.get("name");
    if (name != null) {
      bookRepository.modifyName(bookId, name);
    }

    String author = params.get("author");
    if (author != null) {
      bookRepository.modifyAuthor(bookId, author);
    }

    String isbn = params.get("isbn");
    if (isbn != null) {
      bookRepository.modifyISBN(bookId, isbn);
    }

    String description = params.get("description");
    if (description != null) {
      bookRepository.modifyDescription(bookId, description);
    }

    String image = params.get("image");
    if (image != null) {
      bookRepository.modifyImage(bookId, image);
    }

    String type = params.get("type");
    if (type != null) {
      bookRepository.modifyType(bookId, type);
    }

    String brief = params.get("brief");
    if (brief != null) {
      bookRepository.modifyBrief(bookId, brief);
    }

    BigDecimal price = new BigDecimal(params.get("price"));
    price = price.setScale(2, BigDecimal.ROUND_HALF_UP);  //保留两位，四舍五入
    if (price.compareTo(BigDecimal.ZERO) > 0) {
      bookRepository.modifyPrice(bookId, price);
    }

    Integer inventory = new Integer(params.get("inventory"));
    if (inventory > 0) {
      bookRepository.modifyInventory(bookId, inventory);
    }
    deleteBookCache(bookId);
  }

  @Override
  public PageInfo<Book> getBooksByPage(Integer num) {
    List<Book> books = getBooks();

    PageInfo<Book> pageInfo = PageHelper
        .startPage(num, 5)
        .doSelectPageInfo(() -> getBooks());

    List<Book> result = new ArrayList<>();
    int st = Math.max((num - 1) * 5, 0);
    int en = Math.min(books.size() - 1, num * 5 - 1);
    for (int i = st; i <= en; i++) {
      result.add(books.get(i));
    }

    pageInfo.setList(result);
    pageInfo.setTotal(books.size());
    return pageInfo;

  }

  @Override
  public void deleteBookCache(Integer bookId) {
    redisUtil.del("homeContent");
    log.info("HomeContent has been deleted from Redis.");

    redisUtil.del("bookList");
    log.info("BookList has been deleted from Redis.");

//    redisUtil.delHead("book-");
//    log.info("Book detail has been deleted from Redis.");

    if (bookId != -1) {
      redisUtil.del("book-" + bookId);
      log.info("Book-" + bookId + " has been deleted from Redis.");
    }
  }

}
