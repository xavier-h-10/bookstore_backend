package com.bookstore.repository;
import com.bookstore.entity.Book;
import com.bookstore.entity.CartItem;
import com.bookstore.dao.CartDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Transactional
public interface CartRepository extends JpaRepository<CartItem, Integer>{
  @Query(value="select * from `cart_item` where `user_id`=?1",nativeQuery=true)
  List<CartItem> getItems(Integer id);

  @Query(value="select * from cart_item where user_id=?1 and active=1", nativeQuery=true)
  List<CartItem> getRealCartItems(Integer id);

  @Query(value="from CartItem where userId = :userId and bookId = :bookId")
  CartItem getCartItemById(@Param("userId")Integer userId, @Param("bookId") Integer bookId);

  @Modifying
  @Query(value="replace into `cart_item` (user_id,book_id,amount,active) values(?1,?2,?3,?4)",nativeQuery = true)
  void addCartItem(Integer userId, Integer bookId, Integer amount, Boolean active);

  @Modifying
  @Query(value="delete from `cart_item` where `user_id`=?1 and `book_id`=?2",nativeQuery = true)
  void deleteCartItem(Integer userId, Integer bookId);

  @Modifying
  @Query(value="delete from `cart_item` where `user_id`=?1", nativeQuery = true)
  void submitCart(Integer userId);

  @Modifying
  @Query(value="update `cart_item` set `active`=?3 where `user_id`=?1 and `book_id`=?2",nativeQuery = true)
  void setCartItem(Integer userId,Integer bookId,Boolean active);



}
