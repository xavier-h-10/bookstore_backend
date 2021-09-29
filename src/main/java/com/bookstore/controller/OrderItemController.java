package com.bookstore.controller;

import com.bookstore.entity.OrderItem;
import com.bookstore.service.OrderItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController {
  OrderItemService orderItemService;

  @Autowired
  OrderItemController(OrderItemService orderItemService) {
    this.orderItemService = orderItemService;
  }

  @RequestMapping("/getOrderItemById")  //通过order_id返回订单书籍数据到详情页
  public List<OrderItem> getOrderItemById(@RequestParam("orderId") Integer orderId) {
    return orderItemService.getOrderItemById(orderId);
  }


}
