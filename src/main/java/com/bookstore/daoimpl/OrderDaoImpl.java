package com.bookstore.daoimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.bookstore.dao.OrderDao;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;
import com.bookstore.entity.OrderStat;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.utils.redisUtils.RedisUtil;
import com.bookstore.utils.sessionUtils.SessionUtil;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class OrderDaoImpl implements OrderDao {

  OrderRepository orderRepository;
  BookRepository bookRepository;
  RedisUtil redisUtil;

  @Autowired
  void setOrderRepository(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Autowired
  void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Autowired
  void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  @Override
  public List<Order> getOrder() {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return null;
    }
    String key = "order-" + userId;
    Object p = redisUtil.get(key);
    List<Order> res = null;
    if (p == null) {
      log.info("Get " + key + " from database.");
      res = orderRepository.getOrderByUserId(userId);
      redisUtil.set(key, JSONArray.toJSON(res));
      redisUtil.expire(key, 600);
    } else {
      log.info("Get " + key + " from Redis.");
      res = JSONArray.parseObject(p.toString(), new TypeReference<List<Order>>() {
      });
    }
    return res;
  }

  @Override
  public Order getOrderByOrderId(Integer orderId) {
    String key = "orderByOrderId-" + orderId;
    Object p = redisUtil.get(key);
    Order res = null;
    if (p == null) {
      log.info("Get " + key + " from database.");
      res = orderRepository.getOrderByOrderId(orderId);
      redisUtil.set(key, JSONArray.toJSON(res));
      redisUtil.expire(key, 600);
    } else {
      log.info("Get " + key + " from Redis.");
      res = JSONArray.parseObject(p.toString(), new TypeReference<Order>() {
      });
    }
    return res;
  }

  @Override
  public List<Order> getAllOrder() {
    String key = "allOrder";
    Object p = redisUtil.get(key);
    List<Order> res = null;
    if (p == null) {
      log.info("Get " + key + " from database.");
      res = orderRepository.getAllOrder();
      redisUtil.set(key, JSONArray.toJSON(res));
      redisUtil.expire(key, 600);
    } else {
      log.info("Get " + key + " from Redis.");
      res = JSONArray.parseObject(p.toString(), new TypeReference<List<Order>>() {
      });
    }
    return res;
  }

  @Override
  public List<OrderStat> getAllOrderByTime(String t1, String t2) {
    Timestamp time_start = new Timestamp(Long.parseLong(t1));
    Timestamp time_end = new Timestamp(Long.parseLong(t2));
    List<Order> tmp = orderRepository.getAllOrderByTime(time_start, time_end);  //拿到订单
    Map<Integer, Integer> map = new HashMap<>();
    BigDecimal total_price = new BigDecimal(0);   //统计订单的价格
    Integer total_amount = 0;

    for (int i = 0; i < tmp.size(); i++) {     //把时间范围内的订单的订单项放到map中
      Order now = tmp.get(i);
      if (now.getTime().getTime() >= time_start.getTime()
          && now.getTime().getTime() <= time_end.getTime()) {
        List<OrderItem> items = now.getOrderItem();
        total_price = total_price.add(now.getPrice());
        for (int j = 0; j < items.size(); j++) {
          OrderItem item = items.get(j);
          Integer bookId = item.getBookId();
          Integer amount = item.getAmount();
          if (map.containsKey(bookId)) {
            map.put(bookId, map.get(bookId) + amount);
          } else {
            map.put(bookId, amount);
          }
        }
      }
    }

    Map<Integer, Integer> sortedMap = map.entrySet().stream()    //对map进行排序
        .sorted(Entry.comparingByValue())
        .collect(
            Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    List<OrderStat> res = new ArrayList<>();

    for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
      System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
      OrderStat orderStat = new OrderStat();
      orderStat.setBookId(entry.getKey());
      orderStat.setAmount(entry.getValue());
      Book book = bookRepository.getBookByBookId(entry.getKey());
      orderStat.setName(book.getName());
      orderStat.setPrice(book.getPrice());
      res.add(orderStat);
      total_amount += entry.getValue();
    }

    Collections.reverse(res);   //倒序排序

    OrderStat orderStat = new OrderStat();
    orderStat.setPrice(total_price);
    orderStat.setAmount(total_amount);
    res.add(orderStat);
    return res;
  }

  @Override
  public List<OrderStat> getOrderByTime(String t1, String t2) {
    Integer userId = SessionUtil.getUserId();
    if (userId == null) {
      return null;
    }
    Timestamp time_start = new Timestamp(Long.parseLong(t1));
    Timestamp time_end = new Timestamp(Long.parseLong(t2));
    List<Order> tmp = orderRepository.getOrderByTime(userId, time_start, time_end);
    Map<Integer, Integer> map = new HashMap<>();
    BigDecimal total_price = new BigDecimal(0);   //统计订单的价格
    Integer total_amount = 0;

    for (int i = 0; i < tmp.size(); i++) {     //把时间范围内的订单的订单项放到map中
      Order now = tmp.get(i);
      if (now.getTime().getTime() >= time_start.getTime()
          && now.getTime().getTime() <= time_end.getTime()) {
        List<OrderItem> items = now.getOrderItem();
        total_price = total_price.add(now.getPrice());
        for (int j = 0; j < items.size(); j++) {
          OrderItem item = items.get(j);
          Integer bookId = item.getBookId();
          Integer amount = item.getAmount();
          if (map.containsKey(bookId)) {
            map.put(bookId, map.get(bookId) + amount);
          } else {
            map.put(bookId, amount);
          }
        }
      }
    }

    Map<Integer, Integer> sortedMap = map.entrySet().stream()    //对map进行排序
        .sorted(Entry.comparingByValue())
        .collect(
            Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    List<OrderStat> res = new ArrayList<>();

    for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
      System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
      OrderStat orderStat = new OrderStat();
      orderStat.setBookId(entry.getKey());
      orderStat.setAmount(entry.getValue());
      Book book = bookRepository.getBookByBookId(entry.getKey());
      orderStat.setName(book.getName());
      orderStat.setPrice(book.getPrice());
      res.add(orderStat);
      total_amount += entry.getValue();
    }

    Collections.reverse(res);

    OrderStat orderStat = new OrderStat();
    orderStat.setPrice(total_price);
    orderStat.setAmount(total_amount);
    res.add(orderStat);

    return res;
  }


}
