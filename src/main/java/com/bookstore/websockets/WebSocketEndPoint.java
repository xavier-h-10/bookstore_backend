/* websocket 20210926 */

package com.bookstore.websockets;

import com.bookstore.config.HttpSessionConfig;
import com.bookstore.websockets.decoders.MessageDecoder;
import com.bookstore.websockets.messages.ChatMessage;
import com.bookstore.websockets.messages.InfoMessage;
import com.bookstore.websockets.messages.Message;
import com.bookstore.websockets.messages.UsersMessage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(
    value = "/webSocket/chat",
    decoders = {MessageDecoder.class},
    configurator = HttpSessionConfig.class
)
@Component
@Slf4j
public class WebSocketEndPoint {

  //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
  private static AtomicInteger onlineNum = new AtomicInteger(0);

  //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
  //此处先用map存储，便于以后拓展，例如私聊。  20210927
  private static ConcurrentHashMap<String, Session> clients = new ConcurrentHashMap<>();


  //客户端建立连接
  @OnOpen
  public void onOpen(Session session, EndpointConfig config) {
//    JSONObject object = SessionUtil.getAuth();
//    System.out.println("hello");
//    System.out.println(session);
    HttpSession httpSession = (HttpSession) config.getUserProperties()
        .get(HttpSession.class.getName());

    //此处不同session登录一个user，会建立两个连接
    Integer userId = (Integer) httpSession.getAttribute("userId");
    String username = (String) httpSession.getAttribute("username");
    log.info("Connection detected. Session Id: {} httpSession Id: {} userId: {} username:{}",
        session.getId(), httpSession.getId(), userId,
        username);

    if (userId != null && username != null) {
      session.getUserProperties().put("userId", userId);
      session.getUserProperties().put("username", username);
      clients.put(session.getId(), session);
      addOnlineCount();
      log.info("Connection opened. Session Id: {} onlineNum: {}", session.getId(), onlineNum);

      sendAll(new InfoMessage(username + " 加入了聊天"));
      sendAll(new UsersMessage(this.getUserList()));
    }
  }

  //客户端关闭连接
  @OnClose
  public void onClose(Session session) {
    log.info("Connection detected. Session Id: {}", session.getId());
    if (clients.containsKey(session.getId())) {
      String username = session.getUserProperties().get("username").toString();
      clients.remove(session.getId());
      subOnlineCount();
      log.info("Connection closed. Session Id: {} onlineNum: {}", session.getId(), onlineNum);
      sendAll(new InfoMessage(username + " 离开了聊天"));
      sendAll(new UsersMessage(this.getUserList()));
    }
  }

  @OnError
  public void onError(Throwable throwable) {
    log.error("Error happened. " + throwable.toString());
    throwable.printStackTrace();
  }

  //群发信息
  public synchronized void sendAll(Message msg) {
    try {
      for (Session s : clients.values()) {
        if (s.isOpen()) {
          log.info("Send Message to all sessionId:{}",s.getId());
          s.getBasicRemote().sendText(msg.toJSONObject());
        }
      }
      log.info("Send Message to all. Message: {}", msg);
    } catch (IOException e) {
      log.error(e.toString());
    }
  }

  public synchronized void sendAllExceptMine(Session session, Message msg) {
    try {
      for (Session s : clients.values()) {
        if (s.isOpen() && s.getId() != session.getId()) {
          s.getBasicRemote().sendText(msg.toJSONObject());
        }
      }
      log.info("Send Message to all except mine. Message: {}", msg);
    } catch (IOException e) {
      log.error(e.toString());
    }
  }

  public synchronized void sendToMine(Session session, Message msg) {
    try {
      if (session.isOpen()) {
        session.getBasicRemote().sendText(msg.toJSONObject());
        log.info("Send Message to mine. Message: {}", msg);
      }
    } catch (IOException e) {
      log.error(e.toString());
    }
  }


  /* 获取当前用户列表 */
  public List<String> getUserList() {
    List<String> users = new ArrayList<>();
    for (Session s : clients.values()) {
      if (s.isOpen()) {
        users.add(s.getUserProperties().get("username").toString());
      }
    }
    return users;
  }


  //收到客户端信息
  @OnMessage
  public void onMessage(final Session session, Message msg) {
    log.info("Message received. Message: {}", msg.toString());
    String sessionId = session.getId();
    if (!clients.containsKey(sessionId)) {
      log.info("User has not logged in. Do not send message. sessionId={}", sessionId);
      return;
    }
    /* Forward the message to everybody */
    if (msg instanceof ChatMessage) {
      final ChatMessage cmsg = (ChatMessage) msg;
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      cmsg.setName((String) session.getUserProperties().get("username"));
      cmsg.setTime(df.format(System.currentTimeMillis()));
      cmsg.setMine(false);
      sendAllExceptMine(session, cmsg);
      cmsg.setMine(true);
      sendToMine(session, cmsg);
    }
  }

  public static void addOnlineCount() {
    onlineNum.incrementAndGet();
  }

  public static void subOnlineCount() {
    onlineNum.decrementAndGet();
  }

}

