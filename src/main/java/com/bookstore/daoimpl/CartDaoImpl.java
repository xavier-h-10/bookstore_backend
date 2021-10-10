package com.bookstore.daoimpl;

import com.bookstore.dao.CartDao;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.CartItem;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.OrderItemRepository;
import com.bookstore.utils.redisUtils.RedisUtil;
import com.bookstore.utils.sessionUtils.SessionUtil;
import java.beans.Transient;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class CartDaoImpl implements CartDao {

  CartRepository cartRepository;
  BookRepository bookRepository;
  OrderRepository orderRepository;
  OrderItemRepository orderItemRepository;

  RedisUtil redisUtil;

  @Autowired
  void setCartRepository(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Autowired
  void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Autowired
  void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  @Autowired
  void setOrderRepository(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Autowired
  void setOrderItemRepository(OrderItemRepository orderItemRepository) {
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  public List<CartItem> getCartItems() {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return null;
    }
    List<CartItem> item = cartRepository.getItems(userId);
    System.out.println("cartSize:" + item.size());
    System.out.println(item);
    return item;
  }

  @Override
  public List<CartItem> getRealCartItems() {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return null;
    }
    List<CartItem> item = cartRepository.getRealCartItems(userId);
    return item;
  }

  @Override
  public void addCartItem(Integer bookId, Integer amount, Boolean active) {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return;
    }
    CartItem item = cartRepository.getCartItemById(userId, bookId);
    if (item != null) {
      amount += item.getAmount();
      System.out.println("addItem:" + userId + " " + bookId + " " + amount);
    }
    cartRepository.addCartItem(userId, bookId, amount, active);
  }

  @Override
  public void setCartItem(Integer bookId, Boolean active) {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return;
    }
    System.out.println("setCartItem" + bookId + " " + active);
    cartRepository.setCartItem(userId, bookId, active);
  }


  @Override
  public void deleteCartItem(Integer bookId) {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return;
    }
    cartRepository.deleteCartItem(userId, bookId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
  public void submitCart(Integer userId) {
    System.out.println("submitCart dao executed");
    if (userId == null) {
      return;
    }
    deleteOrderCache(userId);
    Timestamp nowDate = new Timestamp(new Date().getTime());
    System.out.println("date: " + nowDate);
    List<CartItem> myCart = cartRepository.getRealCartItems(userId);
    //订单项为空时不应该加订单
    if (myCart == null) {
      return;
    }
    BigDecimal myPrice = BigDecimal.ZERO;

    for (int i = 0; i < myCart.size(); i++) {
      CartItem myItem = myCart.get(i);
      //此处利用事务回滚操作
      Book myBook = bookRepository.getBookByBookId(myItem.getBookId());
      bookRepository.modifyInventory(myItem.getBookId(),
          myBook.getInventory() - myItem.getAmount());
      myPrice = myPrice.add(BigDecimal.valueOf(myItem.getAmount()).multiply(myBook.getPrice()));
    }

    orderRepository.addOrder(userId, nowDate, myPrice); //加订单
    cartRepository.submitCart(userId);  //清空购物车

    Order myOrder = orderRepository.getMaxOrder();

    for (int i = 0; i < myCart.size(); i++) {
      CartItem myItem = myCart.get(i);
      Book myBook = bookRepository.getBookByBookId(myItem.getBookId());

      BigDecimal amount = new BigDecimal(myItem.getAmount().toString());
      orderItemRepository.addItem(myOrder.getOrderId(), myBook.getBookId(), myItem.getAmount(),
          amount.multiply(myBook.getPrice()));    //增加订单项
    }
  }

  //添加订单
  @Override
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
//  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
  //为了测试不同事务属性，此处进行改写 20210929
  public List<CartItem> addOrder(Integer userId) {
    log.info("addOrder dao executed.");
    if (userId == null) {
      return null;
    }
    deleteOrderCache(userId);
    Timestamp nowDate = new Timestamp(new Date().getTime());
    List<CartItem> myCart = cartRepository.getRealCartItems(userId);
    //订单项为空时不应该加订单
    if (myCart == null) {
      return null;
    }
    BigDecimal myPrice = BigDecimal.ZERO;
    for (int i = 0; i < myCart.size(); i++) {
      CartItem myItem = myCart.get(i);
      Book myBook = bookRepository.getBookByBookId(myItem.getBookId());
      myPrice = myPrice.add(BigDecimal.valueOf(myItem.getAmount()).multiply(myBook.getPrice()));
    }

    orderRepository.addOrder(userId, nowDate, myPrice); //加订单
    log.info("addOrder dao completed.");
    return myCart;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
  public void addOrderItem(Integer userId) {
    if (userId == null) {
      return;
    }
    log.info("addOrderItem dao executed.");
    Order myOrder = orderRepository.getMaxOrder();
    List<CartItem> myCart = cartRepository.getRealCartItems(userId);
    for (int i = 0; i < myCart.size(); i++) {
      CartItem myItem = myCart.get(i);
      Book myBook = bookRepository.getBookByBookId(myItem.getBookId());
      BigDecimal amount = new BigDecimal(myItem.getAmount().toString());
      orderItemRepository.addItem(myOrder.getOrderId(), myBook.getBookId(), myItem.getAmount(),
          amount.multiply(myBook.getPrice()));    //增加订单项
    }
    log.info("addOrderItem dao completed.");
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
  public void deleteCart(Integer userId) {
    if (userId == null) {
      return;
    }
    log.info("deleteCart dao executed.");
    cartRepository.submitCart(userId);  //清空购物车
    log.info("deleteCart dao completed.");
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
//  @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
  public void updateBookInventory(Integer userId, List<CartItem> myCart) {
    if (userId == null) {
      return;
    }
    log.info("updateBookInventory dao executed.");
    //订单项为空时不应该加订单
    if (myCart == null) {
      return;
    }
    for (int i = 0; i < myCart.size(); i++) {
      CartItem myItem = myCart.get(i);
      //此处利用事务回滚操作
      Book myBook = bookRepository.getBookByBookId(myItem.getBookId());
      bookRepository.modifyInventory(myItem.getBookId(),
          myBook.getInventory() - myItem.getAmount());
    }
    log.info("updateBookInventory dao completed.");
  }


  //下订单时应该将原来的订单缓存删除
  @Override
  public void deleteOrderCache(Integer userId) {
    String key1 = "order-" + userId;
    String key2 = "allOrder";
    redisUtil.del(key1);
    log.info(key1 + " has been deleted from Redis.");

    redisUtil.del(key2);
    log.info(key2 + " has been deleted from Redis.");
  }
}
