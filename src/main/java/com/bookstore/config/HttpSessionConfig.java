package com.bookstore.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * 重写握手方法 从而达到使websocket对象可以访问到httpsession中的对象
 */
public class HttpSessionConfig extends Configurator {

  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    HttpSession httpSession = (HttpSession) request.getHttpSession();
    sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
  }
}


