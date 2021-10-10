package com.bookstore.dao;

import com.bookstore.entity.CartItem;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

public interface CartDao {
  List<CartItem> getCartItems();

  void addCartItem(Integer bookId, Integer amount, Boolean active);

  void setCartItem(Integer bookId,Boolean active);

  void deleteCartItem(Integer bookId);

  void submitCart(Integer userId);

  List<CartItem> getRealCartItems();



  //测试事务属性

  List<CartItem> addOrder(Integer userId);

  void addOrderItem(Integer userId);

  void deleteCart(Integer userId);

  void updateBookInventory(Integer userId, List<CartItem> myItem);

  void deleteOrderCache(Integer userId);

}
