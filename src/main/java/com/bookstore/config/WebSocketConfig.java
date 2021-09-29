/* websocket配置 20210926 */
package com.bookstore.config;

import com.bookstore.listener.RequestListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfig {
  /**
   * ServerEndpointExporter 作用
   *
   * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
   *
   * @return
   */
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

  @Autowired
  private RequestListener requestListener;

  @Bean
  public ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean() {
    ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
    servletListenerRegistrationBean.setListener(requestListener);
    return servletListenerRegistrationBean;
  }



}
