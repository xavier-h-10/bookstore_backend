package com.bookstore.dao;

import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;

import com.bookstore.entity.OrderStat;
import java.sql.Timestamp;
import java.util.List;

public interface OrderDao {
  List<Order> getOrder();

  Order getOrderByOrderId(Integer orderId);

  List<Order> getAllOrder();

  List<OrderStat> getOrderByTime(String t1, String t2);

  List<OrderStat> getAllOrderByTime(String t1, String t2);
}
