package com.bookstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;
import com.bookstore.entity.OrderStat;
import com.bookstore.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Timestamp;

@RestController
public class OrderController {
  OrderService orderService;

  @Autowired
  void setOrderService(OrderService orderService) {this.orderService=orderService;}

  @RequestMapping("/getOrderByTime") //管理员根据时间，筛选所有的订单
  public List<OrderStat> getOrderByTime(@RequestParam("t1") String t1, @RequestParam("t2") String t2)
  {
    return orderService.getOrderByTime(t1,t2);
  }

  @RequestMapping("/getAllOrderByTime") //用户根据时间，筛选自己的订单
  public List<OrderStat> getAllOrderByTime(@RequestParam("t1") String t1, @RequestParam("t2") String t2)
  {
    return orderService.getAllOrderByTime(t1,t2);
  }


  @RequestMapping("/getOrderByOrderId")
  public Order getOrderByOrderId(@RequestParam("id") Integer orderId){
    return orderService.getOrderByOrderId(orderId);
  }


  @RequestMapping("/getOrder")   // 通过user_id查找当前用户所有的订单，返回订单数据
  public String getOrder() {
    List<Order> ans=orderService.getOrder();
    //此处必须拒绝重复引用
    String jsonOutput= JSON.toJSONString(ans, SerializerFeature.DisableCircularReferenceDetect);
    System.out.println(jsonOutput);
    return jsonOutput;
  }

  @RequestMapping("/getAllOrder")   //管理员查看系统中所有订单
  public String getAllOrder() {
    List<Order> ans=orderService.getAllOrder();
    String jsonOutput= JSON.toJSONString(ans, SerializerFeature.DisableCircularReferenceDetect);
    System.out.println(jsonOutput);
    return jsonOutput;
  }

}
