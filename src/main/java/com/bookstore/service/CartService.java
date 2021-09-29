package com.bookstore.service;

import com.bookstore.entity.CartItem;
import java.util.List;
import java.util.Map;

public interface CartService {
  List<CartItem> getCartItems();

  List<CartItem> getRealCartItems();

  void addCartItem(Integer bookId, Integer amount, Boolean active);

  void setCartItem(Integer bookId,Boolean active);

  void deleteCartItem(Integer bookId);

  void submitCart(Integer userId);


}
