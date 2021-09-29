/* 监听order队列 20210921 */
package com.bookstore.listener;

import com.bookstore.service.CartService;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

  CartService cartService;

  @Autowired
  void setCartService(CartService cartService) {
    this.cartService = cartService;
  }

  @JmsListener(destination = "order_queue")
  public void receiveOrder(Integer userId) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println("received order at time:" + df.format(new Date())+" userId:"+userId);
    try {
      cartService.submitCart(userId);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
