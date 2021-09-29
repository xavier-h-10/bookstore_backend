package com.bookstore.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

//配置参考:https://blog.csdn.net/u014252478/article/details/86167563
@Component
public class RequestListener implements ServletRequestListener  {
  public void requestInitialized(ServletRequestEvent sre)  {
    //将所有request请求都携带上httpSession
    ((HttpServletRequest) sre.getServletRequest()).getSession();

  }
  public RequestListener() {
  }

  public void requestDestroyed(ServletRequestEvent arg0)  {
  }
}

