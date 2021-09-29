package com.bookstore.repository;

import com.bookstore.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>  {
  @Modifying
  @Query(value="insert into order_item(order_id,book_id,amount,price) values(?1,?2,?3,?4)",nativeQuery = true)
  void addItem(Integer orderId,Integer bookId,Integer amount, BigDecimal price);

  @Query(value="select * from order_item where order_id=?1",nativeQuery = true)
  List<OrderItem> getOrderItemById(Integer orderId);
}
