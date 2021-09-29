package com.bookstore.serviceimpl;

import com.bookstore.dao.CartDao;
import com.bookstore.entity.CartItem;
import com.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//spring就会注入一个代理类，然后每次调用的时候会根据你的作用域去获取bean
@Service
//@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope(value="prototype")
public class CartServiceImpl implements CartService {

  CartDao cartDao;

  @Autowired
  void setCartDao(CartDao cartDao) {
    this.cartDao = cartDao;
  }

  @Override
  public List<CartItem> getCartItems() {
    return cartDao.getCartItems();
  }

  @Override
  public List<CartItem> getRealCartItems() {
    return cartDao.getRealCartItems();
  }

  @Override
  public void addCartItem(Integer bookId, Integer amount, Boolean active) {
    cartDao.addCartItem(bookId, amount, active);
  }

  @Override
  public void setCartItem(Integer bookId, Boolean active) {
    cartDao.setCartItem(bookId, active);
  }

  @Override
  public void deleteCartItem(Integer bookId) {
    cartDao.deleteCartItem(bookId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
//  @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
  public void submitCart(Integer userId) {
    //为了测试事务属性，此处改为四步下单
//    cartDao.submitCart(userId);
    List<CartItem> myItem = cartDao.addOrder(userId);
    cartDao.addOrderItem(userId);
    cartDao.deleteCart(userId);
    cartDao.updateBookInventory(userId, myItem);
  }

}
