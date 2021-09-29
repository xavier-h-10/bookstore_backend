package com.bookstore.repository;

import com.bookstore.entity.Book;

import com.bookstore.entity.CartItem;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer>  {
  @Query(value="from Book where enabled=true")
  List<Book> getBooks();

  @Query(value="from Book where bookId = :bookId")
  Book getBookByBookId(@Param("bookId")Integer id);

  @Modifying
  @Query(value="update `book` set `inventory`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyInventory(Integer book_id, Integer amount);

  @Modifying
  @Query(value="update `book` set `enabled`=0 where `book_id`=?1",nativeQuery = true)
  void deleteBookByBookId(Integer book_id);

  @Modifying
  @Query(value="insert into `book`(name,author,price,isbn,inventory,description,image,type,brief,enabled) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,1)",nativeQuery = true)
  void addBook(String name,String Author,BigDecimal Price,String ISBN,Integer Inventory,String Description,String Image,String Type,String Brief);

  @Query(value="select * from `book` where `name` like ?1 and `enabled`=true",nativeQuery = true)
  List<Book> getBookByName(String name);

  @Modifying
  @Query(value="update `book` set `name`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyName(Integer book_id, String name);

  @Modifying
  @Query(value="update `book` set `author`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyAuthor(Integer book_id, String author);

  @Modifying
  @Query(value="update `book` set `price`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyPrice(Integer book_id, BigDecimal price);

  @Modifying
  @Query(value="update `book` set `isbn`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyISBN(Integer book_id, String ISBN);

  @Modifying
  @Query(value="update `book` set `description`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyDescription(Integer book_id, String description);

  @Modifying
  @Query(value="update `book` set `image`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyImage(Integer book_id, String image);

  @Modifying
  @Query(value="update `book` set `type`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyType(Integer book_id, String type);

  @Modifying
  @Query(value="update `book` set `brief`=?2 where `book_id`=?1",nativeQuery = true)
  void modifyBrief(Integer book_id, String brief);

}
