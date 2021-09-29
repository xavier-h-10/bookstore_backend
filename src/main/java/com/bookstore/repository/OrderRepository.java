package com.bookstore.repository;

import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {

  @Query(value = "select * from `order` where `user_id`=?1", nativeQuery = true)
  List<Order> getOrderByUserId(Integer userId);

  @Query(value = "select * from `order` where `order_id`=?1", nativeQuery = true)
  Order getOrderByOrderId(Integer orderId);

  @Query(value = "select * from `order` where `order_id`=(select max(`order_id`) from `order`)", nativeQuery = true)
  Order getMaxOrder();

  @Modifying
  @Query(value = "insert into `order`(`user_id`, `time`,`price`) values(?1,?2,?3)", nativeQuery = true)
  void addOrder(Integer userId, Timestamp time, BigDecimal price);

  @Query(value = "select * from `order` where `time`>=?1 and `time`<=?2", nativeQuery = true)
  List<Order> getAllOrderByTime(Timestamp time1, Timestamp time2);

  @Query(value = "select * from `order` where `time`>=?2 and `time`<=?3 and `user_id`=?1", nativeQuery = true)
  List<Order> getOrderByTime(Integer userId,Timestamp time1, Timestamp time2);

  @Query(value="select * from `order`",nativeQuery = true)
  List<Order> getAllOrder();
}
