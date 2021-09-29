package com.bookstore.service;

import com.bookstore.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
  List<OrderItem> getOrderItemById(Integer orderId);

}
