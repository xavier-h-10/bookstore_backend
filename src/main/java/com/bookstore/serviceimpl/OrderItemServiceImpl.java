package com.bookstore.serviceimpl;

import com.bookstore.dao.OrderItemDao;
import com.bookstore.service.OrderItemService;
import com.bookstore.entity.OrderItem;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

  OrderItemDao orderItemDao;

  @Autowired
  void setOrderItemDao(OrderItemDao orderItemDao) {
    this.orderItemDao = orderItemDao;
  }

  @Override
  public List<OrderItem> getOrderItemById(Integer orderId) {
    return orderItemDao.getOrderItemById(orderId);
  }

}
