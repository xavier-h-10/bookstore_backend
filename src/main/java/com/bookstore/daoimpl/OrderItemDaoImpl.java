package com.bookstore.daoimpl;

import com.bookstore.dao.OrderItemDao;
import com.bookstore.entity.OrderItem;
import com.bookstore.repository.OrderItemRepository;
import com.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao{
  OrderItemRepository orderItemRepository;
  OrderRepository orderRepository;

  @Autowired
  void setOrderItemRepository(OrderItemRepository orderItemRepository)  {
    this.orderItemRepository=orderItemRepository;
  }

  @Autowired
  void setOrderRepository(OrderRepository orderRepository)  {
    this.orderRepository=orderRepository;
  }

  @Override
  public List<OrderItem> getOrderItemById(Integer orderId) {
    List<OrderItem> ans=orderItemRepository.getOrderItemById(orderId);
    System.out.println("getOrderItem:"+ans);
    return ans;
  }

}
